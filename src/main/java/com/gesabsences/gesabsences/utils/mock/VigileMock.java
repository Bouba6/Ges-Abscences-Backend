package com.gesabsences.gesabsences.utils.mock;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Entities.Vigile;
import com.gesabsences.gesabsences.data.Enum.Role;
import com.gesabsences.gesabsences.data.Repositories.VigileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class VigileMock implements CommandLineRunner {

    private final VigileRepository vigileRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Vigile> vigiles = vigileRepository.findAll();
        if (vigiles.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                Vigile vigile = new Vigile();
                vigile.setNom("Vigile 1");
                User user = new User();
                user.setLogin("vigile1");
                user.setPassword("vigile1");
                user.setRole(Role.VIGILE);
                vigile.setUser(user);
                vigileRepository.save(vigile);
            }
        }
    }

}
