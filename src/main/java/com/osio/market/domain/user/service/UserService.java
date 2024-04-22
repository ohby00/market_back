package com.osio.market.domain.user.service;

import com.osio.market.domain.user.dto.RegisterDTO;
import com.osio.market.domain.user.entity.User;

import java.util.List;

public interface UserService {

    // 사용자 회원가입
    User saveUser(RegisterDTO registerDTO);

    // 사용자 삭제
    void deleteUserByUserId(long id);

    // 사용자 수정
    User updateUser(User user);

    // 사용자 리스트 조회
    List<User> userList();

}
