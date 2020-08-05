package com.arcticgreetings.greetingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("greetings")
    public String getGreetings() {
        return "Welcome to the Arctic, my friend!";
    }
}
