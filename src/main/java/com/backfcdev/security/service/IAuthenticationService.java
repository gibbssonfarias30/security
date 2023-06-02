package com.backfcdev.security.service;

import com.backfcdev.security.dto.AuthRequest;
import com.backfcdev.security.dto.AuthResponse;
import com.backfcdev.security.dto.UserDTO;

public interface IAuthenticationService {
    UserDTO register(UserDTO request);

    AuthResponse authenticate(AuthRequest request);
}
