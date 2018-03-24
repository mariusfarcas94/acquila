package com.acquila.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Used for random testing purposes.
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello, Acquila user! ";
    }
}
