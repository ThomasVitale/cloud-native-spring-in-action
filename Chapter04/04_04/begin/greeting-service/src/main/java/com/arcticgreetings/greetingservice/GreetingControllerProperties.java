package com.arcticgreetings.greetingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerProperties {
    private final GreetingProperties greetingProperties;

    public GreetingControllerProperties(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }

    @GetMapping("greetings/properties")
    public String getGreetings() {
        return greetingProperties.getMessage();
    }
}
