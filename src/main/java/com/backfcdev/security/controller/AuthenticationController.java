package com.backfcdev.security.controller;


import com.backfcdev.security.dto.AuthRequest;
import com.backfcdev.security.dto.AuthResponse;
import com.backfcdev.security.dto.UserDTO;
import com.backfcdev.security.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;


    @PostMapping("/sign-up")
    ResponseEntity<UserDTO> register(@RequestBody UserDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/sign-in")
    ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
