package com.arcticgreetings.greetingservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerValue {

    @Value("${greetings.message}")
    private String greetingMessage;

    @GetMapping("greetings/value")
    public String getGreetings() {
        return greetingMessage;
    }
}
