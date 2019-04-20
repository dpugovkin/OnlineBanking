package com.pugovkin.onlinebanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class HomeController {

    @GetMapping
    public String mainPage() {
        System.out.println("test redirect1");
        return "redirect:/client/all";
    }
}
