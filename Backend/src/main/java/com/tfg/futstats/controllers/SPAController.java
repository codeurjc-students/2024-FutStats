package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SPAController {
    @GetMapping({ "/**/{path:[^\\.]*}" })
    public String redirect() {
        return "forward:/index.html";
    }
}
