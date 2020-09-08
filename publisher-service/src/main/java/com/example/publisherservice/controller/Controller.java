package com.example.publisherservice.controller;

import com.example.publisherservice.model.User;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@EnableBinding(Source.class)
@RestController
public class Controller {

    @Autowired
    private MessageChannel output;

    @GetMapping("/user/create/{name}")
    public String publishEvent(@PathVariable String name) {
        User user = new User(name, new Date());
        output.send(MessageBuilder.withPayload(user)
                .setHeader("header", "newUser")
                .build());
        return "User with name " + user.getName() + " has been created";
    }

}
