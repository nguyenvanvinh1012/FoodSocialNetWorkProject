package com.example.foodsocialproject.controller.client;

import com.example.foodsocialproject.entity.UserInfo;
import com.example.foodsocialproject.entity.Users;
import com.example.foodsocialproject.exception.AlreadyExistsException;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.services.UserInfoService;
import com.example.foodsocialproject.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String post(){


        return "client/home/createPost";
    }
}
