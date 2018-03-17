package com.acquila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.log4j.Log4j2;

/**
 * Acquila application main class.
 */
@Log4j2
@SpringBootApplication
@ComponentScan("com.acquila")
public class AcquilaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcquilaApplication.class, args);
    }
}
