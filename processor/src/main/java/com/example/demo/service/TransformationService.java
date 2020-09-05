package com.example.demo.service;

import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.UUID;

@EnableBinding(Processor.class)
public class TransformationService {

    Logger logger = LoggerFactory.getLogger(TransformationService.class);

    @Autowired
    Processor processor;

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val)
                .setHeader("fromProcessor", "transformedUser")
                .build();
    }

    @StreamListener(target = Processor.INPUT, condition = "headers['header']=='newUser'")
    public void consumeUser(User user) {
       User transformedUser = transformUser(user);
        System.out.println("User with id " + user.getId() + " has been transformed");
       processor.output().send(message(transformedUser));
    }

    private User transformUser(User user){
        user.setId(UUID.randomUUID());
        user.setEmail(user.getName() + "@kafka.com");
        return user;
    }

}
