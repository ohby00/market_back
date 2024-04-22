package com.osio.market.domain.user.controller;


import com.osio.market.domain.user.dto.LoginDTO;
import com.osio.market.domain.user.dto.TokenDTO;
import com.osio.market.domain.user.service.UserService;
import com.osio.market.domain.user.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    /*
    http://localhost:8080/user/register
    {
        "email" : "ooo111@naver",
        "password" : "1234",
        "username" : "찐따소",
        "phone" : "010-0000-0000",
        "address" : "삼천포"
    }
     */
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(
            @RequestBody RegisterDTO request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    /*
      http://localhost:8080/user/login
       {
           "email" : "ooo111@naver",
           "password" : "1234"
       }
    */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(
            @RequestBody LoginDTO request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
