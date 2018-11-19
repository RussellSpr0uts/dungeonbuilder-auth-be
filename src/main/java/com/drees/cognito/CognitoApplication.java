package com.drees.cognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * Cognito Application used to manage the backend registration, authorization,
 * profile management, and logout for Cognito user pools.
 *
 */
@SpringBootApplication()
@EnableAsync
public class CognitoApplication {

    public static void main(final String[] args) {
      SpringApplication.run(CognitoApplication.class, args);
    }
 
}
