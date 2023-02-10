package com.matskevich.springcourse.FirstRestApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody over each method
@RequestMapping("/api")
public class FirstRESTController {

       // get data, no views
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world";   // return data
    }
}
