package com.gesabsences.gesabsences.utils.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Entities.Vigile;
import com.gesabsences.gesabsences.data.Enum.Role;
import com.gesabsences.gesabsences.data.Repositories.UserRepository;
import com.gesabsences.gesabsences.data.Repositories.VigileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Order(3)
public class VigileMock implements CommandLineRunner {

    private final VigileRepository vigileRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        int globalcount = 0;
        List<Vigile> vigiles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String rawPassword = "vigile" + globalcount;

            // Vérifie si l'utilisateur existe déjà
            String login = "vigile" + globalcount;
            if (userRepository.findByLogin(login).isPresent()) {
                globalcount++;
                continue;
            }

            Vigile vigile = new Vigile();
            vigile.setNom("Vigile " + i);
            User user = new User();
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(rawPassword)); // <--- ENCODAGE ICI
            user.setRole(Role.VIGILE);
            vigile.setUser(user);
            userRepository.save(user);
            vigiles.add(vigile);
            globalcount++;
        }

        vigileRepository.saveAll(vigiles);
    }
}