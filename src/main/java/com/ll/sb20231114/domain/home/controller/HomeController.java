package com.ll.sb20231114.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goArticleList() {
        return "redirect:/article/list";
    }
}