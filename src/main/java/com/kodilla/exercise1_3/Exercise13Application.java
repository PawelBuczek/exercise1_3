package com.kodilla.exercise1_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Exercise13Application {

    public static void main(String[] args) {
        SpringApplication.run(Exercise13Application.class, args);
        new File("data/output/my-file-list.txt").deleteOnExit();
    }

}
