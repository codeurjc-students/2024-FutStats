package com.tfg.futstats.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {
    @GetMapping({ "/**/{path:[^\\.]*}" })
    public String redirect() {
        return "forward:/index.html";
    }
}
