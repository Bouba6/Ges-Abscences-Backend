package com.gesabsences.gesabsences.utils.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.data.Entities.Admin;
import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Entities.Vigile;
import com.gesabsences.gesabsences.data.Enum.Role;
import com.gesabsences.gesabsences.data.Repositories.AdminRepository;
import com.gesabsences.gesabsences.data.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class AdminMock implements CommandLineRunner{

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
       int globalcount = 0;
        List<Admin> admins = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String rawPassword = "Admin" + globalcount;

            // Vérifie si l'utilisateur existe déjà
            String login = "Admin" + globalcount;
            if (userRepository.findByLogin(login).isPresent()) {
                globalcount++;
                continue;
            }

            Admin admin = new Admin();
            admin.setNom("Admin" + i);
            User user = new User();
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(rawPassword)); // <--- ENCODAGE ICI
            user.setRole(Role.VIGILE);
            admin.setUser(user);
            userRepository.save(user);
            admins.add(admin);
            globalcount++;
        }

        adminRepository.saveAll(admins);
    }
    
}
