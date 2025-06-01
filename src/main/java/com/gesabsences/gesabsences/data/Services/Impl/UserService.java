package com.gesabsences.gesabsences.data.Services.Impl;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new RuntimeException("Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}