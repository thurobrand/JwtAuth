package com.savgrac.JWTDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResourse {

    @RequestMapping({"/hello"})
    public String hello(){
        return "Hello World";
    }
}
