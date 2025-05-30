package com.gesabsences.gesabsences.utils.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.data.Repositories.ModuleRepository;

import lombok.RequiredArgsConstructor;

import com.gesabsences.gesabsences.data.Entities.Module;

@RequiredArgsConstructor
@Component
@Order(1)
public class ModuleMock implements CommandLineRunner {

    private final ModuleRepository moduleRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Module> modules = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Module module = new Module();
            module.setNom("Module " + i);
            module.setCoef((int) (Math.random() * 4) + 1);
            modules.add(module);
        }

        moduleRepository.saveAll(modules);
    }

}
