package com.bandi.swiggy.assignment.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Main application which starts the swiggy driver-assignment server.
 * 
 * Server starts by default in 8080 port. (which can be configured by using -Dserver.port in the java run command).
 * 
 * localhost:8080
 * 
 * @author kishore.bandi
 *
 */
@SpringBootApplication(scanBasePackages = "com.bandi")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
