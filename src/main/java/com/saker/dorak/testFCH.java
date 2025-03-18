package com.saker.dorak;

import com.saker.dorak.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class testFCH implements CommandLineRunner {

    private final userService userService;

    @Autowired
    public testFCH(userService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        //userService.createAccount("abrahim majadly","abrahem","ab123456@gmail.com","asdasdgdfgg","iphone 15 pro max", "77.356.48.122");
    }
}
