package com.arcticgreetings.greetingservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerEnvironment {
    private final Environment environment;

    public GreetingControllerEnvironment(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("greetings/environment")
    public String getGreetings() {
        return environment.getProperty("greetings.message");
    }
}
