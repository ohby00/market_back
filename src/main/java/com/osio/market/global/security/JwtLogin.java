package com.osio.market.global.security;

import com.osio.market.domain.user.dto.EmailDTO;
import com.osio.market.domain.user.dto.LoginDTO;
import com.osio.market.domain.user.dto.RegisterDTO;
import com.osio.market.domain.user.dto.TokenDTO;
import com.osio.market.domain.user.entity.Role;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import com.osio.market.global.util.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtLogin {
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtLogin(UserJpaRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public EmailDTO getUserByAccessToken(String accessToken) {
        String username = jwtService.extractUsername(accessToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToDTO(user);
    }

    private EmailDTO convertToDTO(User user) {
        EmailDTO dto = new EmailDTO();
        dto.setEmail(user.getEmail());
        return dto;
    }

    public TokenDTO refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (jwtService.validateToken(refreshToken, userDetails)) {
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            return TokenDTO.builder()
                    .accessToken(newAccessToken)
                    .build();
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
