package com.example.foodsocialproject.controller.client;

import com.example.foodsocialproject.dto.FileUploadUtil;
import com.example.foodsocialproject.dto.RegistrationRequest;
import com.example.foodsocialproject.entity.*;
import com.example.foodsocialproject.event.RegistrationCompleteEvent;
import com.example.foodsocialproject.exception.AlreadyExistsException;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.services.PostsService;
import com.example.foodsocialproject.services.StepService;
import com.example.foodsocialproject.services.UserInfoService;
import com.example.foodsocialproject.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {""})
public class HomeController {
    private final UserServices userService;
    private final PostsService postsService;
    private final UserInfoService userInfoService;

    @GetMapping("")
    public String index(Model model,Principal p){
        List<Posts> listPosts = postsService.getList().stream().sorted(Comparator.comparing(Posts::getCreatedAt,Comparator.reverseOrder())).collect(Collectors.toList());
        model.addAttribute("listPosts",listPosts);
        String userEmail = p.getName();
        Users foundUser = userService.findbyEmail(userEmail).get();
        model.addAttribute("user",foundUser);
        List<Posts> topPosts = postsService.get5PostsTopLike();
        model.addAttribute("topPosts",topPosts);
        return "client/home/index";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "client/auth-login";
    }
    @GetMapping("/profile")
    public String profile(Principal p, Model model){
        String userEmail = p.getName();
        Users foundUser = userService.findbyEmail(userEmail).get();
        return getString(model, foundUser);
    }

    @GetMapping("/profile/{id}")
    public String seeProfile(@PathVariable("id") UUID id, Principal p, Model model){
        Users foundUser = userService.findById(id);
        return getString(model, foundUser);
    }

    @GetMapping("/post-edit/{id}")
    public String editPost(@PathVariable("id") UUID id, Model model){
        Posts foundPosts = postsService.findByID(id);
    model.addAttribute("recipe", foundPosts);
        return "client/home/createPost";
    }

    private String getString(Model model, Users foundUser) {
        List<Posts> listPosts = foundUser.getPosts().stream().sorted(Comparator.comparing(Posts::getCreatedAt,Comparator.reverseOrder())).collect(Collectors.toList());
        model.addAttribute("user",foundUser);
        model.addAttribute("userInfo",foundUser.getUserInfo());
        model.addAttribute("listPosts",listPosts);
        if (listPosts.isEmpty()){
            model.addAttribute("listPostsEmpty",true);
        }
        return "client/home/profile";
    }

    @PostMapping("/updateInfo")
    public String updateInfo(@Valid @ModelAttribute("userInfo") UserInfo userInfo, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile multipartFile, Model model, Principal p) {
        String userEmail = p.getName();
        Users foundUser = userService.findbyEmail(userEmail).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("user",foundUser);
            model.addAttribute("userInfo",foundUser.getUserInfo());
            model.addAttribute("error", "Xảy ra lỗi.");
            model.addAttribute("listPosts",foundUser.getPosts());
            return "client/home/profile";
        }
        try {
            String mainFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            userInfo.getUser().setAvatarUrl(mainFileName);
            userInfoService.save(userInfo, foundUser);
            String uploadDir = "./avatar-images/" + foundUser.getId();
            FileUploadUtil.saveFile(uploadDir,multipartFile, mainFileName);
            model.addAttribute("success", "Cập nhật thành công.");
        } catch (ResourceNotFoundException e) {
            System.out.println("Has Error "+e.getMessage());
            model.addAttribute("error", "Xảy ra lỗi.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("user",foundUser);
        model.addAttribute("userInfo",foundUser.getUserInfo());
        model.addAttribute("listPosts",foundUser.getPosts());

        return "client/home/profile";
    }

    @GetMapping("/post")
    public String post(Model model){
        Posts recipe = new Posts();
        model.addAttribute("recipe",recipe);
        return "client/home/createPost";
    }

    @PostMapping("/createRecipe")
    public String save(@Valid @ModelAttribute("recipe") Posts recipe, BindingResult bindingResult,Principal p, Model model, @RequestParam("numberOfImages") int numberOfImages, RedirectAttributes ra, @RequestParam("imageFile") MultipartFile multipartFile, @RequestParam("extraImage") MultipartFile[] extraMultipartFile ) throws IOException {
        String userEmail = p.getName();
        Users foundUser = userService.findbyEmail(userEmail).get();
        if (bindingResult.hasErrors()) {
            return "client/home/createPost";
        }
       String mainFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
       recipe.setImage(mainFileName);

       int count=0;
        List<Steps> stepsList = new ArrayList<Steps>();
       for (MultipartFile extraMultiPart : extraMultipartFile){
           String extraImageName = StringUtils.cleanPath(extraMultiPart.getOriginalFilename());
           if (count<=numberOfImages){
               Steps step = new Steps();
               step.setStepImg(extraImageName);
               step.setStepNumber(count+1);
               step.setRecipe(recipe);
                step.setStepDescription(recipe.getSteps().get(count).getStepDescription());
               stepsList.add(step);
           }
          count++;
       }

       recipe.setSteps(stepsList);
        recipe.setUser(foundUser);
        for (Ingredients ingredient:recipe.getIngredients()
        ) {
            ingredient.setRecipe(recipe);
        }
        Posts savedRecipe = postsService.save(recipe);

        String postUploadDir = "./post-images/" + savedRecipe.getId();
        String stepUploadDir = "./steps-images/" + savedRecipe.getId();
        FileUploadUtil.saveFile(postUploadDir,multipartFile, mainFileName);

       for (MultipartFile extraMultiFile : extraMultipartFile){
           String fileName = StringUtils.cleanPath(extraMultiFile.getOriginalFilename());

           FileUploadUtil.saveFile(stepUploadDir,extraMultiFile, fileName);
       }


        ra.addFlashAttribute("raMessage", "Đăng bài thành công");
        model.addAttribute("user",foundUser);
        model.addAttribute("userInfo",foundUser.getUserInfo());
        model.addAttribute("listPosts",foundUser.getPosts());
        return "client/home/profile";
    }

    @GetMapping("/post-details/{id}")
    public String getPostDetails(@PathVariable("id") UUID id, Model model){
        Posts post = postsService.findByID(id);
        List<Steps> stepsList = post.getSteps().stream().sorted(Comparator.comparing(Steps::getStepNumber)).toList();
        post.setSteps(stepsList);
        model.addAttribute("post",post);
        return "client/home/post_details";

    }
}
