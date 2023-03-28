package com.mpolder.mancala.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AngularController implements ErrorController {
    @RequestMapping(value = {"games/**", "invites/**"})
    public String serve() {
        return "forward:/index.html";
    }
}