package com.gesabsences.gesabsences.security;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Your user repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Recherche utilisateur: " + username);
        
        // Replace with your actual user entity and repository
        User user = userRepository.findByLogin(username)
            .orElseThrow(() -> {
                System.out.println("❌ Utilisateur non trouvé dans la DB: " + username);
                return new UsernameNotFoundException("User not found: " + username);
            });
        
        System.out.println("✅ Utilisateur trouvé dans DB: " + user.getLogin());
        System.out.println("🔒 Password hash dans DB: " + user.getPassword());
        System.out.println("🏷️ Role: " + user.getRole());
        
        return new CustomUserDetails(user);
    }
}