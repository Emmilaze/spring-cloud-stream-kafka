package com.example.subscriberservice.service;

import com.example.subscriberservice.model.User;
import com.example.subscriberservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @StreamListener(target = Sink.INPUT, condition = "headers['fromProcessor']=='transformedUser'")
    public void consumeMessage(User user) {
        userRepository.save(user);
        LOGGER.info("User: " + user.toString() + " saved to DB");
    }

}
