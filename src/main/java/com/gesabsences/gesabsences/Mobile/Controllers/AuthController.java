package com.gesabsences.gesabsences.Mobile.Controllers;

import com.gesabsences.gesabsences.Mobile.Dto.Request.UserRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Response.JwtResponse;
import com.gesabsences.gesabsences.config.JwtUtil;
import com.gesabsences.gesabsences.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserRequest authenticationRequest,
            HttpServletResponse response)
            throws Exception {

        System.out.println("=== DEBUT LOGIN ===");
        System.out.println("Username reçu: " + authenticationRequest.getLogin());
        System.out.println("Password reçu: " + authenticationRequest.getPassword());

        try {
            // Debug: Check if user exists before authentication
            System.out.println("🔍 Tentative de chargement de l'utilisateur...");
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());

            // Now try authentication
            System.out.println("🚀 Tentative d'authentification...");
            authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());
            System.out.println("✅ Authentication réussie");

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

            final String token = jwtTokenUtil.generateToken(userDetails.getUsername(),
                    customUserDetails.getUser().getRole().name());
            System.out.println("✅ Token généré");

            jakarta.servlet.http.Cookie jwtCookie = new jakarta.servlet.http.Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // true en production avec HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 7 jours
            // jwtCookie.setSameSite("Strict"); // Si tu veux l'ajouter

            response.addCookie(jwtCookie);
            System.out.println("✅ Cookie JWT créé");

            return ResponseEntity.ok(new JwtResponse(token,
                    customUserDetails.getUser().getRole().name(),
                    customUserDetails.getUser().getId(),
                    customUserDetails.getUser().getNom(),
                    customUserDetails.getUser().getPrenom()));

        } catch (UsernameNotFoundException e) {
            System.out.println("❌ Utilisateur non trouvé: " + e.getMessage());
            throw new Exception("USER_NOT_FOUND", e);
        } catch (Exception e) {
            System.out.println("❌ Erreur dans login: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            System.out.println("🔐 Création du token d'authentification...");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            System.out.println("🔍 Token créé, tentative d'authentification via AuthenticationManager...");

            authenticationManager.authenticate(authToken);
            System.out.println("✅ AuthenticationManager a validé l'authentification");

        } catch (DisabledException e) {
            System.out.println("❌ Compte désactivé");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("❌ Identifiants invalides - vérifiez l'encodage du mot de passe");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Supprimer le cookie
        jakarta.servlet.http.Cookie jwtCookie = new jakarta.servlet.http.Cookie("jwt_token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // true en production
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Supprime immédiatement

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().body("Déconnexion réussie");
    }
}