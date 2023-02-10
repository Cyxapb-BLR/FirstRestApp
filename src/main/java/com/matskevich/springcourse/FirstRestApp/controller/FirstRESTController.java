package com.matskevich.springcourse.FirstRestApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class FirstRESTController {

    @ResponseBody   // get data, no views
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world";   // return data
    }
}
