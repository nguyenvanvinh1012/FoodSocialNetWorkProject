package com.example.foodsocialproject.controller.client;

import com.example.foodsocialproject.dto.RegistrationRequest;
import com.example.foodsocialproject.entity.Posts;
import com.example.foodsocialproject.entity.UserInfo;
import com.example.foodsocialproject.entity.Users;
import com.example.foodsocialproject.event.RegistrationCompleteEvent;
import com.example.foodsocialproject.exception.AlreadyExistsException;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.services.UserInfoService;
import com.example.foodsocialproject.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {""})
public class HomeController {
    private final UserServices userService;
    private final UserInfoService userInfoService;

    @GetMapping("")
    public String index(){
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
        model.addAttribute("user",foundUser);
        model.addAttribute("userInfo",foundUser.getUserInfo());

        return "client/home/profile";
    }

    @PostMapping("/updateInfo")
    public String updateInfo(@Valid @ModelAttribute("userInfo") UserInfo userInfo, BindingResult bindingResult, Model model, Principal p) {
        String userEmail = p.getName();
        Users foundUser = userService.findbyEmail(userEmail).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("user",foundUser);
            model.addAttribute("userInfo",foundUser.getUserInfo());
            model.addAttribute("error", "Xảy ra lỗi.");
            return "client/home/profile";
        }
        try {
             userInfoService.save(userInfo);
            model.addAttribute("success", "Cập nhật thành công.");
        } catch (ResourceNotFoundException e) {
            System.out.println("Has Error "+e.getMessage());
            model.addAttribute("error", "Xảy ra lỗi.");
        }
        model.addAttribute("user",foundUser);
        model.addAttribute("userInfo",foundUser.getUserInfo());
        return "client/home/profile";
    }

    @GetMapping("/post")
    public String post(Model model){
        Posts recipe = new Posts();
        model.addAttribute("recipe",recipe);
        return "client/home/createPost";
    }

    @PostMapping("/createRecipe")
    public String save(@Valid @ModelAttribute("recipe") Posts recipe, BindingResult bindingResult, RedirectAttributes re) throws IOException {
        if (bindingResult.hasErrors()) {
            return "client/home/createPost";
        }
        System.out.println("RECIPE "+recipe.getIngredients().toString());
        System.out.println("RECIPE "+recipe.getSteps().toString());
     /*    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
       city.setImage_path(fileName);
        City savedCity = cityService.save(city);
        String uploadDir = "./cities-images/" + savedCity.getId();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save uploaded file " + e.getMessage());
        }
        re.addFlashAttribute("raMessage", "Lưu thành phố thành công.");*/
        return "client/home/createPost";
    }
}
