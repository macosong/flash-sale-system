package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findByName(String name);
    void intsert(User user);
}