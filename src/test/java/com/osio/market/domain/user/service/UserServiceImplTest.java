package com.osio.market.domain.user.service;

import com.osio.market.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void save() {
        User user1 = User.builder()
                .id(5L)
                .email("obw@123")
                .password("123")
                .phone("000-1111-1111")
                .address("충북")
                .build();

    }

}