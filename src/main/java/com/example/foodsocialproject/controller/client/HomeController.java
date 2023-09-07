package com.example.foodsocialproject.controller.client;

import com.example.foodsocialproject.dto.RegistrationRequest;
import com.example.foodsocialproject.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {""})
public class HomeController {
    @GetMapping("")
    public String index(){
        return "client/home/index";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "client/auth-login";
    }
}
