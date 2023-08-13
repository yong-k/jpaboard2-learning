package com.godcoder.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 경로 없이 지정해서, 기본으로 여기 들어오게끔
    @GetMapping
    public String index() {
        return "index";
    }
}
