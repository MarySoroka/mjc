package com.epam.esm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/hi")
    public String sayHello() {
        return "hi";
    }
}