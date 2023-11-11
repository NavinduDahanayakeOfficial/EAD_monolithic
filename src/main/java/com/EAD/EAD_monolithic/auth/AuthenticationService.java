package com.EAD.EAD_monolithic.auth;

import com.EAD.EAD_monolithic.Exception.EmailAlreadyExistException;
import com.EAD.EAD_monolithic.Exception.EmailOrPasswordIncorrectException;
import com.EAD.EAD_monolithic.config.JwtService;
import com.EAD.EAD_monolithic.entity.User;
import com.EAD.EAD_monolithic.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> existingUserOptional = repository.findByEmail(request.getEmail());
        if (existingUserOptional.isPresent()) {
            /*User existingUser = existingUserOptional.get();*/
            throw new EmailAlreadyExistException("Email Already Exists");
        }
        else{
            var user= User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .deliveryAddress(request.getDeliveryAddress())
                    .role(request.getRole())
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException ex) {
            throw new EmailOrPasswordIncorrectException("Email or Password is incorrect");
        }
    }
}
