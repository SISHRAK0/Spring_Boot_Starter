package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiniControllers {

    @GetMapping("/test")
    public String test() {
        return "Hello, world!";
    }

    @GetMapping("/info")
    public String info() {
        return "Information from InfoController!";
    }

    @GetMapping("/bye")
    public String bye() {
        return "Goodbye from ByeController!";
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello from HelloController!";
    }

}
