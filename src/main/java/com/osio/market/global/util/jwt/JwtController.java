package com.osio.market.global.util.jwt;

import com.osio.market.domain.user.dto.EmailDTO;
import com.osio.market.domain.user.dto.LoginDTO;
import com.osio.market.domain.user.dto.TokenDTO;
import com.osio.market.global.security.JwtLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/jwt")
@RestController
@CrossOrigin
@Slf4j
public class JwtController {
    /*
    http://localhost:8080/jwt/login
    {
    "email" : "quddnr217@naver.com",
    "password" : "1234"
    }
     */

    @Autowired
    JwtLogin jwtLogin;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> jwtLogin(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(jwtLogin.login(loginDTO));
    }

    @GetMapping("/page")
    public ResponseEntity<EmailDTO> jwtMyPage(@RequestHeader("Authorization") String authorizationHeader) {
        // Authorization 헤더에서 토큰 추출
        String accessToken = authorizationHeader.replace("Bearer ", "");

        // JwtLogin 서비스를 사용하여 토큰을 통해 사용자 정보 가져오기
        EmailDTO emailDTO = jwtLogin.getUserByAccessToken(accessToken);

        return ResponseEntity.ok(emailDTO);
    }
}
