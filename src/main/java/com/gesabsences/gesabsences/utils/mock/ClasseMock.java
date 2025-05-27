package com.gesabsences.gesabsences.utils.mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Entities.Module;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurModule;
import com.gesabsences.gesabsences.data.Enum.NiveauState;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Repositories.ClasseRepository;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.ModuleRepository;
import com.gesabsences.gesabsences.data.Repositories.NiveauRepository;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurClasseRepository;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurModuleRepository;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurRepository;

@RequiredArgsConstructor
@Component
@Order(2)
public class ClasseMock implements CommandLineRunner {

    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;
    private final ProfesseurRepository professeurRepository;
    private final ProfesseurClasseRepository professeurClasseRepository;
    private final ProfesseurModuleRepository professeurModuleRepository;
    private final ModuleRepository moduleRepository;
    private final EleveRepository eleveRepository;
    private final CoursRepository coursRepository;
    private final AbscenceRepository absenceRepository;

    // Predefined data to ensure more structured generation
    private static final List<String> FIRST_NAMES = Arrays.asList(
            "Mamadou", "Fatou", "Cheikh", "Awa", "Ousmane", "Aminata", "Modou", "Mariama",
            "Ibrahima", "Ndèye", "Abdoulaye", "Sokhna", "Serigne", "Dieynaba", "Babacar");

    private static final List<String> LAST_NAMES = Arrays.asList(
            "Diop", "Ndiaye", "Ba", "Fall", "Sow", "Gueye", "Faye", "Camara", "Sarr",
            "Diallo", "Sy", "Ndoye");

    private static final List<String> MODULE_NAMES = Arrays.asList(
            "Mathématiques", "Français", "Anglais", "Histoire-Géographie",
            "Sciences Physiques", "SVT", "Éducation Civique");

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DÉBUT DE LA GÉNÉRATION DES DONNÉES ===");

        try {
            // Clear existing data
            cleanupExistingData();
            System.out.println("✅ Nettoyage des données terminé");

            // Ensure modules exist
            List<Module> modules = ensureModulesExist();
            System.out.println("✅ Modules créés : " + modules.size());

            // Generate mock data
            generateMockData(modules);

            System.out.println("=== RÉSULTATS FINAUX ===");
            System.out.println("Nombre de cours créés : " + coursRepository.count());
            System.out.println("Nombre d'absences créées : " + absenceRepository.count());
            System.out.println("Nombre de professeurs : " + professeurRepository.count());
            System.out.println("Nombre de classes : " + classeRepository.count());
            System.out.println("Nombre d'élèves : " + eleveRepository.count());

        } catch (Exception e) {
            System.err.println("❌ ERREUR CRITIQUE dans la génération : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void cleanupExistingData() {
        System.out.println("🧹 Nettoyage des données existantes...");
        try {
            absenceRepository.deleteAll();
            System.out.println("  - Absences supprimées");

            coursRepository.deleteAll();
            System.out.println("  - Cours supprimés");

            eleveRepository.deleteAll();
            System.out.println("  - Élèves supprimés");

            professeurClasseRepository.deleteAll();
            System.out.println("  - Relations ProfesseurClasse supprimées");

            professeurModuleRepository.deleteAll();
            System.out.println("  - Relations ProfesseurModule supprimées");

            classeRepository.deleteAll();
            System.out.println("  - Classes supprimées");

            niveauRepository.deleteAll();
            System.out.println("  - Niveaux supprimés");

            professeurRepository.deleteAll();
            System.out.println("  - Professeurs supprimés");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du nettoyage : " + e.getMessage());
            throw e;
        }
    }

    private List<Module> ensureModulesExist() {
        System.out.println("📚 Vérification/création des modules...");
        List<Module> existingModules = moduleRepository.findAll();
        if (existingModules.isEmpty()) {
            List<Module> modulesToSave = MODULE_NAMES.stream()
                    .map(name -> {
                        Module module = new Module();
                        module.setNom(name);
                        return module;
                    })
                    .collect(Collectors.toList());
            existingModules = moduleRepository.saveAll(modulesToSave);
            System.out.println("  ✅ " + existingModules.size() + " modules créés");
        } else {
            System.out.println("  ✅ " + existingModules.size() + " modules existants trouvés");
        }
        return existingModules;
    }

    private void generateMockData(List<Module> modules) {
        System.out.println("🏗️ Génération des données mockées...");
        Random random = new Random();

        // Create Niveaux and Classes
        List<NiveauState> niveauxStates = Arrays.asList(
                NiveauState.SIXIEME, NiveauState.CINQUIEME,
                NiveauState.QUATRIEME, NiveauState.TROISIEME);

        // Lists to store all generated entities
        List<Professeur> allProfesseurs = new ArrayList<>();
        List<Classe> allClasses = new ArrayList<>();

        // Phase 1: Create Niveaux and Classes
        System.out.println("📋 Phase 1: Création des niveaux et classes...");
        for (NiveauState niveauState : niveauxStates) {
            Niveau niveau = createNiveau(niveauState);

            // Create 2-3 classes per niveau
            for (int i = 1; i <= 2 + random.nextInt(2); i++) {
                Classe classe = createClasse(niveau, niveauState, i);
                allClasses.add(classe);
            }
        }
        System.out.println("  ✅ " + allClasses.size() + " classes créées");

        // Phase 2: Create Professeurs
        System.out.println("👨‍🏫 Phase 2: Création des professeurs...");
        allProfesseurs = generateProfesseurs(1 + random.nextInt(3), modules); // 8-12 professeurs
        System.out.println("  ✅ " + allProfesseurs.size() + " professeurs créés");

        // Phase 3: Assign professors to classes
        System.out.println("🔗 Phase 3: Attribution des professeurs aux classes...");
        assignProfesseursToClasses(allProfesseurs, allClasses);
        System.out.println("  ✅ Attribution terminée");

        // Phase 4: Generate students for each class
        System.out.println("👥 Phase 4: Génération des élèves...");
        int totalEleves = 0;
        for (Classe classe : allClasses) {
            List<Eleve> eleves = generateEleves(classe, 20 + random.nextInt(10));
            totalEleves += eleves.size();
        }
        System.out.println("  ✅ " + totalEleves + " élèves créés");

        // Phase 5: Generate courses
        System.out.println("📅 Phase 5: Génération des cours...");
        generateCourses(allProfesseurs, modules);
    }

    private Niveau createNiveau(NiveauState niveauState) {
        Niveau niveau = new Niveau();
        niveau.setNiveauState(niveauState);
        return niveauRepository.save(niveau);
    }

    private Classe createClasse(Niveau niveau, NiveauState niveauState, int classNumber) {
        Classe classe = new Classe();
        classe.setNomClasse(niveauState + " Classe " + classNumber);
        classe.setNiveau(niveau);
        return classeRepository.save(classe);
    }

    private List<Professeur> generateProfesseurs(int count, List<Module> modules) {
        Random random = new Random();
        List<Professeur> professeurs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            try {
                Professeur professeur = new Professeur();
                professeur.setNom(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())));
                professeur.setPrenom(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())));
                professeur.setEmail(professeur.getPrenom().toLowerCase() + "." +
                        professeur.getNom().toLowerCase() + "@ecole.com");
                professeur.setDateNaissance(
                        LocalDate.of(1975 + random.nextInt(20), 1 + random.nextInt(12), 1 + random.nextInt(28)));
                professeur.setSexe(random.nextBoolean() ? "M" : "F");
                professeur.setAdresse(random.nextInt(100) + " Rue de l'École");
                professeur.setVille("Paris");

                // Save the professor first
                Professeur savedProfesseur = professeurRepository.save(professeur);

                // Create ProfesseurModule relationships
                List<Module> shuffledModules = new ArrayList<>(modules);
                Collections.shuffle(shuffledModules);
                int moduleCount = 1 + random.nextInt(2); // 1-2 modules par professeur pour éviter la surcharge

                List<ProfesseurModule> professeurModules = shuffledModules.stream()
                        .limit(moduleCount)
                        .map(module -> {
                            ProfesseurModule pm = new ProfesseurModule();
                            pm.setProfesseur(savedProfesseur);
                            pm.setModule(module);
                            return pm;
                        })
                        .collect(Collectors.toList());

                professeurModuleRepository.saveAll(professeurModules);
                savedProfesseur.setProfesseurModules(professeurModules);

                professeurs.add(savedProfesseur);
            } catch (Exception e) {
                System.err.println("❌ Erreur création professeur " + i + " : " + e.getMessage());
            }
        }

        return professeurs;
    }

    private void assignProfesseursToClasses(List<Professeur> professeurs, List<Classe> classes) {
        Random random = new Random();

        if (professeurs.isEmpty()) {
            System.err.println("❌ Aucun professeur à assigner !");
            return;
        }

        System.out
                .println("  🔄 Attribution de " + professeurs.size() + " professeurs à " + classes.size() + " classes");

        for (Classe classe : classes) {
            try {
                // Assign a main professor
                Professeur profPrincipal = professeurs.get(random.nextInt(professeurs.size()));
                classe.setProfPrincipal(profPrincipal);
                classeRepository.save(classe);

                // Assign 2-3 professors to each class (réduit pour éviter la surcharge)
                List<Professeur> shuffledProfs = new ArrayList<>(professeurs);
                Collections.shuffle(shuffledProfs);
                int profCount = Math.min(2 + random.nextInt(2), professeurs.size()); // 2-3 professeurs max

                for (int i = 0; i < profCount; i++) {
                    ProfesseurClasse professeurClasse = new ProfesseurClasse();
                    professeurClasse.setProfesseur(shuffledProfs.get(i));
                    professeurClasse.setClasse(classe);
                    professeurClasseRepository.save(professeurClasse);
                }
            } catch (Exception e) {
                System.err.println("❌ Erreur attribution classe " + classe.getNomClasse() + " : " + e.getMessage());
            }
        }

        // Update professor relationships - CRITIQUE: reload depuis la DB
        System.out.println("  🔄 Mise à jour des relations professeurs...");
        for (Professeur professeur : professeurs) {
            try {
                // Recharger le professeur depuis la DB pour avoir les relations à jour
                Professeur reloadedProf = professeurRepository.findById(professeur.getId()).orElse(null);
                if (reloadedProf != null) {
                    List<ProfesseurClasse> professeurClasses = professeurClasseRepository
                            .findByProfesseur(reloadedProf);
                    reloadedProf.setProfesseurClasses(professeurClasses);
                    professeurRepository.save(reloadedProf);
                }
            } catch (Exception e) {
                System.err
                        .println("❌ Erreur mise à jour professeur " + professeur.getPrenom() + " : " + e.getMessage());
            }
        }
    }

    private List<Eleve> generateEleves(Classe classe, int count) {
        Random random = new Random();
        List<Eleve> eleves = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Eleve eleve = new Eleve();
            eleve.setNom(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())));
            eleve.setPrenom(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())));
            eleve.setDateNaissance(LocalDate.now().minusYears(11 + random.nextInt(4)));
            eleve.setSexe(random.nextBoolean() ? "M" : "F");
            eleve.setAdresse(random.nextInt(100) + " Rue de l'École");
            eleve.setVille("Paris");
            eleve.setEmail(eleve.getPrenom().toLowerCase() + "." +
                    eleve.getNom().toLowerCase() + "@eleve.com");
            eleve.setClasse(classe);
            eleves.add(eleve);
        }

        eleveRepository.saveAll(eleves);
        classe.setEtudiants(eleves);
        classe.setEffectifs(eleves.size());
        classeRepository.save(classe);

        return eleves;
    }

    private void generateCourses(List<Professeur> allProfesseurs, List<Module> modules) {
        Random random = new Random();
        List<Cours> coursList = new ArrayList<>();

        // Récupérer toutes les classes FRAÎCHES depuis la DB
        List<Classe> allClasses = classeRepository.findAll();

        // Prédéfinir des créneaux horaires
        List<LocalTime> timeSlots = Arrays.asList(
                LocalTime.of(8, 0), LocalTime.of(9, 30),
                LocalTime.of(11, 0), LocalTime.of(14, 0),
                LocalTime.of(15, 30));

        // Déterminer la plage de dates (cette semaine)
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        System.out.println("  📊 État actuel :");
        System.out.println("    - Professeurs : " + allProfesseurs.size());
        System.out.println("    - Classes : " + allClasses.size());
        System.out.println("    - Modules : " + modules.size());

        // CRITIQUE: Recharger les professeurs depuis la DB avec leurs relations
        List<Professeur> refreshedProfesseurs = professeurRepository.findAll();
        System.out.println("    - Professeurs rechargés : " + refreshedProfesseurs.size());

        int totalCoursGeneres = 0;
        int professeursAvecCours = 0;

        // Pour chaque professeur
        for (Professeur professeur : refreshedProfesseurs) {
            try {
                // Récupérer ses modules DEPUIS LA DB
                List<ProfesseurModule> profModules = professeurModuleRepository.findByProfesseur(professeur);
                List<Module> professeurModules = profModules.stream()
                        .map(ProfesseurModule::getModule)
                        .collect(Collectors.toList());

                // Récupérer ses classes DEPUIS LA DB
                List<ProfesseurClasse> profClasses = professeurClasseRepository.findByProfesseur(professeur);
                List<Classe> professorClasses = profClasses.stream()
                        .map(ProfesseurClasse::getClasse)
                        .collect(Collectors.toList());

                System.out.println("  👨‍🏫 " + professeur.getPrenom() + " " + professeur.getNom() +
                        " - Modules: " + professeurModules.size() + ", Classes: " + professorClasses.size());

                // Skip si pas de modules ou classes
                if (professeurModules.isEmpty() || professorClasses.isEmpty()) {
                    System.out.println("    ⚠️ Ignoré (pas de modules ou classes)");
                    continue;
                }

                professeursAvecCours++;

                // Générer 2-3 cours pour ce professeur (réduit pour éviter surcharge)
                int coursCount = 2 + random.nextInt(2);

                for (int i = 0; i < coursCount; i++) {
                    try {
                        Cours cours = new Cours();

                        // Sélectionner un module et une classe
                        Module module = professeurModules.get(random.nextInt(professeurModules.size()));
                        Classe classe = professorClasses.get(random.nextInt(professorClasses.size()));

                        cours.setClasse(classe);
                        cours.setModule(module);
                        cours.setProfesseur(professeur);

                        // Date du cours (lundi à vendredi)
                        LocalDate courseDate = startOfWeek.plusDays(random.nextInt(5));
                        cours.setDate(courseDate);

                        // Créneau horaire
                        LocalTime heureDebut = timeSlots.get(random.nextInt(timeSlots.size()));
                        cours.setHeureDebut(heureDebut);
                        cours.setHeureFin(heureDebut.plusHours(1));
                        cours.setNbrHeures(1);

                        coursList.add(cours);
                        totalCoursGeneres++;

                    } catch (Exception e) {
                        System.err.println("    ❌ Erreur création cours " + i + " : " + e.getMessage());
                    }
                }

                System.out.println("    ✅ " + coursCount + " cours générés");

            } catch (Exception e) {
                System.err
                        .println("  ❌ Erreur traitement professeur " + professeur.getPrenom() + " : " + e.getMessage());
            }
        }

        System.out.println("  📈 Statistiques génération :");
        System.out
                .println("    - Professeurs avec cours : " + professeursAvecCours + "/" + refreshedProfesseurs.size());
        System.out.println("    - Total cours à sauvegarder : " + coursList.size());

        // Sauvegarder tous les cours
        if (!coursList.isEmpty()) {
            try {
                System.out.println("  💾 Sauvegarde des cours...");
                List<Cours> savedCours = coursRepository.saveAll(coursList);
                System.out.println("  ✅ " + savedCours.size() + " cours sauvegardés avec succès");

                // Générer les absences
                generateAbsences(savedCours);
            } catch (Exception e) {
                System.err.println("  ❌ Erreur lors de la sauvegarde des cours : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("  ❌ Aucun cours à sauvegarder !");
        }
    }

    private void generateAbsences(List<Cours> coursList) {
        Random random = new Random();
        List<Abscence> absencesList = new ArrayList<>();

        System.out.println("  🏃‍♂️ Génération des absences pour " + coursList.size() + " cours...");

        for (Cours cours : coursList) {
            try {
                List<Eleve> eleves = eleveRepository.findByClasse(cours.getClasse());

                for (Eleve eleve : eleves) {
                    // 10% de chance d'être absent (réduit pour éviter trop d'absences)
                    if (random.nextDouble() < 0.10) {
                        Abscence absence = new Abscence();
                        absence.setEleve(eleve);
                        absence.setCours(cours);
                        absence.setTypeAbscence(TypeAbscence.Absent);
                        absence.setStatutAbscence(StatutAbscence.ENATTENTE);
                        absence.setJustificatif("Absence non justifiée");

                        absencesList.add(absence);
                    }
                }
            } catch (Exception e) {
                System.err.println("    ❌ Erreur génération absences cours " + cours.getId() + " : " + e.getMessage());
            }
        }

        try {
            if (!absencesList.isEmpty()) {
                absenceRepository.saveAll(absencesList);
                System.out.println("  ✅ " + absencesList.size() + " absences générées et sauvegardées");
            } else {
                System.out.println("  ℹ️ Aucune absence générée");
            }
        } catch (Exception e) {
            System.err.println("  ❌ Erreur sauvegarde absences : " + e.getMessage());
            e.printStackTrace();
        }
    }
}