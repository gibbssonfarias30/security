package com.backfcdev.security.service;


import com.backfcdev.security.dto.AuthRequest;
import com.backfcdev.security.dto.AuthResponse;
import com.backfcdev.security.dto.UserDTO;
import com.backfcdev.security.enums.Role;
import com.backfcdev.security.jwt.JwtProvider;
import com.backfcdev.security.model.User;
import com.backfcdev.security.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService{

    private final JwtProvider jwtProvider;

    private final IUserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;


    @Override
    public UserDTO register(UserDTO request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return convertToDto(userRepository.save(user));
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(EntityNotFoundException::new);

        var jwtToken = jwtProvider.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }



    private UserDTO convertToDto(User entity){
        return mapper.map(entity, UserDTO.class);
    }
}
