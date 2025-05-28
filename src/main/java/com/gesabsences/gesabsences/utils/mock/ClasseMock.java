package com.gesabsences.gesabsences.utils.mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justitfication;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Entities.Module;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurModule;
import com.gesabsences.gesabsences.data.Enum.NiveauState;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Repositories.ClasseRepository;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.JustificatifRepository;
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
    private final JustificatifRepository justificatifRepository;

    // Predefined data to ensure more structured generation
    private static final List<String> FIRST_NAMES = Arrays.asList(
            "Mamadou", "Fatou", "Cheikh", "Awa", "Ousmane", "Aminata", "Modou", "Mariama",
            "Ibrahima", "Ndèye", "Abdoulaye", "Sokhna", "Serigne", "Dieynaba", "Babacar");

    private static final List<String> LAST_NAMES = Arrays.asList(
            "Diop", "Ndiaye", "Ba", "Fall", "Sow", "Gueye", "Faye", "Camara", "Sarr",
            "Diallo", "Sy", "Ndoye");

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearDatabase();
    }

    private void clearDatabase() {
        System.out.println("🧹 Nettoyage de la base de données...");

        justificatifRepository.deleteAll();
        absenceRepository.deleteAll();
        coursRepository.deleteAll();
        professeurClasseRepository.deleteAll();
        professeurModuleRepository.deleteAll();
        professeurRepository.deleteAll();
        eleveRepository.deleteAll();
        classeRepository.deleteAll();
        niveauRepository.deleteAll();

        System.out.println("✅ Base de données vidée avec succès");
    }

    @Transactional
    private void generateMockData(List<Module> modules) {
        System.out.println("🚀 Génération des données mockées...");
        Random random = new Random();

        // Phase 1: Créer les niveaux et classes
        List<Classe> allClasses = createNiveauxAndClasses();
        System.out.println("✅ Phase 1 terminée: " + allClasses.size() + " classes créées");

        // Phase 2: Créer les professeurs
        List<Professeur> allProfesseurs = createProfesseurs(modules);
        System.out.println("✅ Phase 2 terminée: " + allProfesseurs.size() + " professeurs créés");

        // Phase 3: Assigner les professeurs aux classes
        assignProfesseursToClasses(allProfesseurs, allClasses);
        System.out.println("✅ Phase 3 terminée: Professeurs assignés aux classes");

        // Phase 4: Créer les élèves
        createStudents(allClasses);
        System.out.println("✅ Phase 4 terminée: Élèves créés");

        // Phase 5: Créer les cours - CORRECTION PRINCIPALE
        List<Cours> coursCrees = createCourses(allProfesseurs, allClasses, modules);
        System.out.println("✅ Phase 5 terminée: " + coursCrees.size() + " cours créés");

        // Phase 6: Créer les absences si des cours existent
        if (!coursCrees.isEmpty()) {
            generateAbsences(coursCrees);
            System.out.println("✅ Phase 6 terminée: Absences générées");
        }
    }

    private List<Classe> createNiveauxAndClasses() {
        System.out.println("📚 Création des niveaux et classes...");
        List<Classe> allClasses = new ArrayList<>();
        Random random = new Random();

        List<NiveauState> niveauxStates = Arrays.asList(
                NiveauState.SIXIEME, NiveauState.CINQUIEME,
                NiveauState.QUATRIEME, NiveauState.TROISIEME);

        for (NiveauState niveauState : niveauxStates) {
            // Créer le niveau
            Niveau niveau = new Niveau();
            niveau.setNiveauState(niveauState);
            niveau = niveauRepository.save(niveau);

            // Créer 2-3 classes par niveau
            int nombreClasses = 2 + random.nextInt(2);
            for (int i = 1; i <= nombreClasses; i++) {
                Classe classe = new Classe();
                classe.setNomClasse(niveauState.toString() + " - Classe " + i);
                classe.setNiveau(niveau);
                classe.setEffectifs(0); // Sera mis à jour lors de la création des élèves

                Classe savedClasse = classeRepository.save(classe);
                allClasses.add(savedClasse);

                System.out.println("   ✓ Classe créée: " + savedClasse.getNomClasse());
            }
        }

        return allClasses;
    }

    private List<Professeur> createProfesseurs(List<Module> modules) {
        System.out.println("👨‍🏫 Création des professeurs...");
        List<Professeur> professeurs = new ArrayList<>();
        Random random = new Random();

        // Créer 8-12 professeurs
        int nombreProfesseurs = 8 + random.nextInt(5);

        for (int i = 0; i < nombreProfesseurs; i++) {
            try {
                // Créer le professeur
                Professeur professeur = new Professeur();
                professeur.setNom(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())));
                professeur.setPrenom(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())));
                professeur.setEmail(professeur.getPrenom().toLowerCase() + "." +
                        professeur.getNom().toLowerCase() + "@ecole.sn");
                professeur.setDateNaissance(
                        LocalDate.of(1970 + random.nextInt(25), 1 + random.nextInt(12), 1 + random.nextInt(28)));
                professeur.setSexe(random.nextBoolean() ? "M" : "F");
                professeur.setAdresse((10 + random.nextInt(90)) + " Avenue Bourguiba");
                professeur.setVille("Dakar");

                // Sauvegarder le professeur
                Professeur savedProfesseur = professeurRepository.save(professeur);

                // Assigner 1-3 modules au professeur
                assignModulesToProfesseur(savedProfesseur, modules);

                professeurs.add(savedProfesseur);
                System.out.println(
                        "   ✓ Professeur créé: " + savedProfesseur.getPrenom() + " " + savedProfesseur.getNom());

            } catch (Exception e) {
                System.err.println("   ❌ Erreur création professeur " + (i + 1) + ": " + e.getMessage());
            }
        }

        return professeurs;
    }

    private void assignModulesToProfesseur(Professeur professeur, List<Module> modules) {
        Random random = new Random();

        // Mélanger les modules et en prendre 1-3
        List<Module> shuffledModules = new ArrayList<>(modules);
        Collections.shuffle(shuffledModules);

        int nombreModules = 1 + random.nextInt(3); // 1 à 3 modules
        nombreModules = Math.min(nombreModules, modules.size());

        for (int i = 0; i < nombreModules; i++) {
            try {
                ProfesseurModule professeurModule = new ProfesseurModule();
                professeurModule.setProfesseur(professeur);
                professeurModule.setModule(shuffledModules.get(i));
                professeurModuleRepository.save(professeurModule);
            } catch (Exception e) {
                System.err.println("     ❌ Erreur assignation module: " + e.getMessage());
            }
        }
    }

    private void assignProfesseursToClasses(List<Professeur> professeurs, List<Classe> classes) {
        System.out.println("🔗 Attribution des professeurs aux classes...");
        Random random = new Random();

        if (professeurs.isEmpty() || classes.isEmpty()) {
            System.err.println("❌ Impossible d'assigner: professeurs ou classes vides");
            return;
        }

        // Étape 1: Assigner un professeur principal à chaque classe
        for (Classe classe : classes) {
            try {
                Professeur profPrincipal = professeurs.get(random.nextInt(professeurs.size()));
                classe.setProfPrincipal(profPrincipal);
                classeRepository.save(classe);

                System.out.println("   ✓ Prof principal " + classe.getNomClasse() +
                        ": " + profPrincipal.getPrenom() + " " + profPrincipal.getNom());
            } catch (Exception e) {
                System.err.println("   ❌ Erreur assignation prof principal: " + e.getMessage());
            }
        }

        // Étape 2: Créer les relations ProfesseurClasse pour TOUS les professeurs
        for (Professeur professeur : professeurs) {
            try {
                // Assigner ce professeur à 1-3 classes aléatoires
                List<Classe> shuffledClasses = new ArrayList<>(classes);
                Collections.shuffle(shuffledClasses);

                int nombreClassesPourProf = 2 + random.nextInt(2); // 2 à 3 classes par prof
                nombreClassesPourProf = Math.min(nombreClassesPourProf, classes.size());

                for (int i = 0; i < nombreClassesPourProf; i++) {
                    Classe classe = shuffledClasses.get(i);

                    // Vérifier si la relation existe déjà
                    boolean relationExists = professeurClasseRepository
                            .findByProfesseur(professeur)
                            .stream()
                            .anyMatch(pc -> pc.getClasse().getId().equals(classe.getId()));

                    if (!relationExists) {
                        ProfesseurClasse professeurClasse = new ProfesseurClasse();
                        professeurClasse.setProfesseur(professeur);
                        professeurClasse.setClasse(classe);
                        professeurClasseRepository.save(professeurClasse);

                        System.out.println("   ✓ Relation créée: " + professeur.getPrenom() +
                                " -> " + classe.getNomClasse());
                    }
                }
            } catch (Exception e) {
                System.err.println("   ❌ Erreur création relations pour " +
                        professeur.getPrenom() + ": " + e.getMessage());
            }
        }
    }

    private void createStudents(List<Classe> classes) {
        System.out.println("👨‍🎓 Création des élèves...");
        Random random = new Random();
        int totalEleves = 0;

        for (Classe classe : classes) {
            try {
                // 15-25 élèves par classe
                int nombreEleves = 15 + random.nextInt(11);
                List<Eleve> eleves = new ArrayList<>();

                for (int i = 0; i < nombreEleves; i++) {
                    Eleve eleve = new Eleve();
                    eleve.setNom(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())));
                    eleve.setPrenom(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())));
                    eleve.setDateNaissance(LocalDate.now().minusYears(11 + random.nextInt(4)));
                    eleve.setSexe(random.nextBoolean() ? "M" : "F");
                    eleve.setAdresse((10 + random.nextInt(90)) + " Rue de Sandaga");
                    eleve.setVille("Dakar");
                    eleve.setEmail(eleve.getPrenom().toLowerCase() + "." +
                            eleve.getNom().toLowerCase() + "@eleve.sn");
                    eleve.setClasse(classe);
                    eleves.add(eleve);
                }

                // Sauvegarder tous les élèves de la classe
                eleveRepository.saveAll(eleves);

                // Mettre à jour les effectifs de la classe
                classe.setEffectifs(eleves.size());
                classeRepository.save(classe);

                totalEleves += eleves.size();
                System.out.println("   ✓ " + classe.getNomClasse() + ": " + eleves.size() + " élèves");

            } catch (Exception e) {
                System.err.println("   ❌ Erreur création élèves pour " + classe.getNomClasse() + ": " + e.getMessage());
            }
        }

        System.out.println("   ✅ Total élèves créés: " + totalEleves);
    }

    // MÉTHODE CORRIGÉE - Nouvelle version avec meilleure logique
    // MÉTHODE CORRIGÉE - Nouvelle version avec sauvegarde des cours
    private List<Cours> createCourses(List<Professeur> professeurs, List<Classe> classes, List<Module> modules) {
        System.out.println("📖 Création des cours...");

        if (professeurs.isEmpty() || classes.isEmpty() || modules.isEmpty()) {
            System.err.println("❌ Impossible de créer les cours: données manquantes");
            System.err.println("   Professeurs: " + professeurs.size());
            System.err.println("   Classes: " + classes.size());
            System.err.println("   Modules: " + modules.size());
            return new ArrayList<>();
        }

        List<Cours> coursList = new ArrayList<>();
        Random random = new Random();

        // Créneaux horaires
        List<LocalTime> timeSlots = Arrays.asList(
                LocalTime.of(8, 0), LocalTime.of(9, 30), LocalTime.of(11, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 30), LocalTime.of(17, 0));

        // Période: seulement cette semaine (5 jours ouvrables)
        LocalDate startDate = LocalDate.now().with(DayOfWeek.MONDAY);
        List<LocalDate> joursOuvrables = new ArrayList<>();

        // Générer 5 jours ouvrables (une seule semaine)
        for (int jour = 0; jour < 5; jour++) { // Lundi à Vendredi
            joursOuvrables.add(startDate.plusDays(jour));
        }

        System.out.println("   📅 Jours ouvrables disponibles: " + joursOuvrables.size());

        // LIMITE STRICTE de 20 cours maximum
        final int LIMITE_COURS = 20;
        int totalCoursGeneres = 0;

        // Créer une liste de toutes les combinaisons possibles
        List<CombinaisonCours> combinaisons = new ArrayList<>();

        for (Professeur professeur : professeurs) {
            try {
                // Récupérer les modules du professeur
                List<ProfesseurModule> profModules = professeurModuleRepository.findByProfesseur(professeur);
                if (profModules.isEmpty()) {
                    System.out.println("   ⚠️ " + professeur.getPrenom() + " n'a aucun module");
                    continue;
                }

                // Récupérer les classes du professeur
                List<ProfesseurClasse> profClasses = professeurClasseRepository.findByProfesseur(professeur);
                if (profClasses.isEmpty()) {
                    System.out.println("   ⚠️ " + professeur.getPrenom() + " n'a aucune classe");
                    continue;
                }

                System.out.println("   👨‍🏫 " + professeur.getPrenom() + " " + professeur.getNom() +
                        " - Modules: " + profModules.size() + ", Classes: " + profClasses.size());

                // Créer les combinaisons pour ce professeur
                for (ProfesseurModule profModule : profModules) {
                    for (ProfesseurClasse profClasse : profClasses) {
                        combinaisons
                                .add(new CombinaisonCours(professeur, profModule.getModule(), profClasse.getClasse()));
                    }
                }

            } catch (Exception e) {
                System.err.println("   Erreur pour professeur " + professeur.getPrenom() + ": " + e.getMessage());
            }
        }

        // Mélanger les combinaisons pour avoir une distribution aléatoire
        Collections.shuffle(combinaisons, random);

        System.out.println("   🔢 Total combinaisons possibles: " + combinaisons.size());
        System.out.println("   🎯 Limite fixée: " + LIMITE_COURS + " cours maximum");

        // Créer les cours jusqu'à la limite STRICTE
        for (CombinaisonCours combinaison : combinaisons) {
            if (totalCoursGeneres >= LIMITE_COURS) {
                System.out.println("   🛑 Limite de " + LIMITE_COURS + " cours atteinte, arrêt de la génération");
                break;
            }

            try {
                Cours cours = new Cours();

                cours.setProfesseur(combinaison.professeur);
                cours.setModule(combinaison.module);
                cours.setClasse(combinaison.classe);

                // Date aléatoire parmi les jours ouvrables
                LocalDate courseDate = joursOuvrables.get(random.nextInt(joursOuvrables.size()));
                // cours.setDate(courseDate);
                // LocalDate courseDate =
                // joursOuvrables.get(random.nextInt(joursOuvrables.size()));

                // Conversion de LocalDate -> Date
                ZoneId zoneId = ZoneId.systemDefault();
                Date convertedDate = Date.from(courseDate.atStartOfDay(zoneId).toInstant());

                cours.setDate(convertedDate);

                // Heure aléatoire
                LocalTime heureDebut = timeSlots.get(random.nextInt(timeSlots.size()));
                cours.setHeureDebut(heureDebut);
                cours.setHeureFin(heureDebut.plusHours(1).plusMinutes(30));
                cours.setNbrHeures(2);

                // ✅ CORRECTION PRINCIPALE: Sauvegarder le cours pour générer son ID
                Cours savedCours = coursRepository.save(cours);
                coursList.add(savedCours);
                totalCoursGeneres++;

                System.out.println("   ✓ Cours créé [ID: " + savedCours.getId() + "] - " +
                        combinaison.professeur.getPrenom() + " - " +
                        combinaison.module.getNom() + " - " +
                        combinaison.classe.getNomClasse());

            } catch (Exception e) {
                System.err.println("     ❌ Erreur création cours: " + e.getMessage());
            }
        }

        System.out.println("   ✅ TOTAL FINAL: " + totalCoursGeneres + " cours générés et sauvegardés (limite: "
                + LIMITE_COURS + ")");
        return coursList;
    }

    // Classe utilitaire pour les combinaisons
    private static class CombinaisonCours {
        final Professeur professeur;
        final Module module;
        final Classe classe;

        public CombinaisonCours(Professeur professeur, Module module, Classe classe) {
            this.professeur = professeur;
            this.module = module;
            this.classe = classe;
        }
    }

    private void generateAbsences(List<Cours> coursList) {
        System.out.println("📋 Génération des absences...");
        Random random = new Random();
        List<Abscence> absencesList = new ArrayList<>();
        List<Justitfication> justificatifs = new ArrayList<>();

        int totalAbsencesGenerees = 0;

        for (Cours cours : coursList) {
            try {
                // Vérifier que le cours a une classe valide
                if (cours.getClasse() == null) {
                    System.out.println("   ⚠️ Cours sans classe détecté, ignoré");
                    continue;
                }

                List<Eleve> eleves = eleveRepository.findByClasse(cours.getClasse());

                if (eleves.isEmpty()) {
                    System.out.println("   ⚠️ Aucun élève trouvé pour la classe " + cours.getClasse().getNomClasse());
                    continue;
                }

                int absencesPourCeCours = 0;

                for (Eleve eleve : eleves) {
                    try {
                        // 15% de chance d'être absent à chaque cours
                        if (random.nextDouble() < 0.15) {
                            // Créer l'absence
                            Abscence absence = new Abscence();
                            absence.setEleve(eleve);
                            absence.setCours(cours);
                            absence.setTypeAbscence(random.nextBoolean() ? TypeAbscence.Absent : TypeAbscence.Retard);
                            absence.setStatutAbscence(StatutAbscence.NON_JUSTIFIER); // Changé de JUSTIFIER à
                                                                                     // NON_JUSTIFIER par défaut

                            absencesList.add(absence);
                            absencesPourCeCours++;
                            totalAbsencesGenerees++;

                            // Créer le justificatif seulement si l'absence sera justifiée (30% des cas)
                            if (random.nextDouble() < 0.3) {
                                absence.setStatutAbscence(StatutAbscence.JUSTIFIER);

                                Justitfication justificatif = new Justitfication();
                                justificatif.setAbscence(absence);
                                justificatif.setStatutJustification(getRandomStatutJustification(random));
                                justificatif.setJustificatif(getRandomJustificatif(random));

                                justificatifs.add(justificatif);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("     ❌ Erreur génération absence pour élève " +
                                eleve.getPrenom() + " " + eleve.getNom() + ": " + e.getMessage());
                    }
                }

                if (absencesPourCeCours > 0) {
                    System.out.println("   ✓ Cours " + cours.getId() + " (" +
                            cours.getClasse().getNomClasse() + " - " +
                            cours.getModule().getNom() + "): " +
                            absencesPourCeCours + " absences");
                }

            } catch (Exception e) {
                System.err
                        .println("   ❌ Erreur génération absences pour cours " + cours.getId() + ": " + e.getMessage());
                e.printStackTrace(); // Pour voir la stack trace complète
            }
        }

        // Sauvegarder les absences d'abord
        try {
            if (!absencesList.isEmpty()) {
                System.out.println("   💾 Sauvegarde de " + absencesList.size() + " absences...");
                List<Abscence> savedAbsences = absenceRepository.saveAll(absencesList);
                System.out.println("   ✅ " + savedAbsences.size() + " absences sauvegardées");

                // Ensuite sauvegarder les justificatifs
                if (!justificatifs.isEmpty()) {
                    System.out.println("   💾 Sauvegarde de " + justificatifs.size() + " justificatifs...");
                    justificatifRepository.saveAll(justificatifs);
                    System.out.println("   ✅ " + justificatifs.size() + " justificatifs sauvegardés");
                }
            } else {
                System.out.println("   ℹ️ Aucune absence générée");
            }
        } catch (Exception e) {
            System.err.println("   ❌ ERREUR CRITIQUE lors de la sauvegarde:");
            System.err.println("   Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la sauvegarde des absences", e);
        }

        System.out.println("   📊 Résumé: " + totalAbsencesGenerees + " absences générées, " +
                justificatifs.size() + " justificatifs créés");
    }

    // Méthodes utilitaires pour générer des données plus réalistes
    private StatutJustification getRandomStatutJustification(Random random) {
        StatutJustification[] statuts = StatutJustification.values();
        return statuts[random.nextInt(statuts.length)];
    }

    private String getRandomJustificatif(Random random) {
        List<String> justificatifs = Arrays.asList(
                "Rendez-vous médical",
                "Maladie",
                "Problème de transport",
                "Affaire familiale urgente",
                "Convocation administrative",
                "Accident de circulation",
                "Panne de véhicule");
        return justificatifs.get(random.nextInt(justificatifs.size()));
    }
    // Méthodes utilitaires pour générer des données plus réalistes

    private void printFinalResults() {
        System.out.println("\n=== 📊 RÉSULTATS FINAUX ===");
        try {
            System.out.println("👨‍🏫 Professeurs créés    : " + professeurRepository.count());
            System.out.println("🏫 Niveaux créés        : " + niveauRepository.count());
            System.out.println("📚 Classes créées       : " + classeRepository.count());
            System.out.println("👨‍🎓 Élèves créés         : " + eleveRepository.count());
            System.out.println("📖 Cours créés          : " + coursRepository.count());
            System.out.println("❌ Absences créées      : " + absenceRepository.count());
            System.out.println("🔗 Relations Prof-Classe: " + professeurClasseRepository.count());
            System.out.println("📝 Relations Prof-Module: " + professeurModuleRepository.count());
            System.out.println("=== ✅ GÉNÉRATION TERMINÉE AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'affichage des résultats: " + e.getMessage());
        }
    }
}