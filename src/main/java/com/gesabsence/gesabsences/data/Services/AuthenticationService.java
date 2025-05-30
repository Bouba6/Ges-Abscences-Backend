package com.gesabsences.gesabsences.data.Services;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Repositories.UserRepository;
import com.gesabsences.dto.LoginRequest;
import com.gesabsences.dto.LoginResponse;
import com.gesabsences.gesabsences.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
            );

            User user = userRepository.findByLogin(request.getLogin())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            String token = jwtUtil.generateToken(user);

            return new LoginResponse(token, user.getRole().toString(), user.getLogin());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Identifiants invalides");
        }
    }
}
 
