package com.osio.market.global.util.jwt;

import com.osio.market.domain.user.dto.*;
import com.osio.market.global.security.JwtLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
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
    public ResponseEntity<RegisterDTO> jwtMyPage(@RequestHeader("Authorization") String access) {
        // Authorization 헤더에서 토큰 추출
        String accessToken = access.replace("Bearer ", "");

        // JwtLogin 서비스를 사용하여 토큰을 통해 사용자 정보 가져오기
        RegisterDTO registerDTO = jwtLogin.getUserByAccessToken(accessToken);

        return ResponseEntity.ok(registerDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshAccessToken(@RequestBody RefreshToken refreshToken, @RequestHeader("Authorization") String accessToken) {
        // Authorization 헤더에서 토큰 추출
        String accessTokenValue = accessToken.replace("Bearer ", "");


        // JwtLogin 서비스를 사용하여 리프래쉬 토큰을 이용해 엑세스 토큰 재발급 받기
        String refreshToken2 = refreshToken.getRefreshToken();
        TokenDTO tokenDTO = jwtLogin.refreshAccessToken(refreshToken2, accessTokenValue);
        return ResponseEntity.ok(tokenDTO);
    }

}
