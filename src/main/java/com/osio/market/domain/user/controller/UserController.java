package com.osio.market.domain.user.controller;


import com.osio.market.domain.user.dto.CheckEmailDTO;
import com.osio.market.domain.user.dto.EmailDTO;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.service.EmailService;
import com.osio.market.domain.user.service.UserService;
import com.osio.market.domain.user.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/user")
@RestController
@CrossOrigin()
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final Map<String, String> codeVerify;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.codeVerify = new HashMap<>();
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

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) {
        if (emailDTO == null ) {
            return new ResponseEntity<>("이메일 주소를 입력하세요.", HttpStatus.BAD_REQUEST);
        }
        try {
            log.info(emailDTO.getEmail());
            String randomCode = generateCode();
            emailService.send(emailDTO.getEmail(), "인증 코드 발송", "인증 코드: " + randomCode);
            codeVerify.put(emailDTO.getEmail(), randomCode);
            return new ResponseEntity<>("이메일 전송 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("이메일 전송 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 랜덤 숫자 생성 (100000 ~ 999999)
        return String.valueOf(code);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody CheckEmailDTO checkEmailDTO) {
        // 이메일 인증 로직을 수행 및 인증 결과에 따른 응답 반환

        String frontCode = checkEmailDTO.getCode();
        log.info("frontCode = {} ",frontCode);

        String email = checkEmailDTO.getEmail();
        log.info("email = {} ",email);

        String generateCode = codeVerify.get(email);
        log.info("generateCode = {} ",generateCode);

        if (frontCode.equals(generateCode)) {
            return new ResponseEntity<>("이메일 인증 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean validCode(String frontCode, String generateCode) {
        return frontCode.equals(generateCode);
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