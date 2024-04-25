package com.osio.market.domain.user.service;


import com.osio.market.domain.user.dto.RegisterDTO;
import com.osio.market.domain.user.entity.Role;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserJpaRepository jpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(RegisterDTO request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role(Role.USER)
                .build();
        return jpaRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserByUserId(long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUserId(long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    @Transactional
    public List<User> getAllUser() {
        return jpaRepository.findAll();
    }


}
