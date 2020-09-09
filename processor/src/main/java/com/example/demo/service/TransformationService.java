package com.example.demo.service;

import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import java.util.UUID;

@EnableBinding(Processor.class)
public class TransformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public User consumeUser(User user) {
        User transformedUser = transformUser(user);
        LOGGER.info("User with id " + user.getId() + " has been transformed");
        return transformedUser;
    }

    private User transformUser(User user) {
        user.setId(UUID.randomUUID());
        user.setEmail(user.getName() + "@kafka.com");
        return user;
    }

}
