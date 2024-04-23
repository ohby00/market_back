package com.osio.market.domain.user.service;

import com.osio.market.domain.user.dto.RegisterDTO;

public interface EmailService {
    void send(String to, String subject, String text);
}
