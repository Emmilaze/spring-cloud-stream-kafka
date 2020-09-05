package com.example.subscriberservice.repository;

import com.example.subscriberservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

}
