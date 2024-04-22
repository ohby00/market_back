package com.osio.market.domain.user.controller;


import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.service.UserService;
import com.osio.market.domain.user.dto.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> selectUserList() {
        List<User> userEntityList = userService.getAllUser();
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> userSave(@RequestBody RegisterDTO registerDTO) {
        User result = userService.saveUser(registerDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/find/{id}")
    public Optional<User> findId(@PathVariable("id") Long id) {
        return userService.findByUserId(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserByUserId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}