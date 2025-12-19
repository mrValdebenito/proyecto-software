package com.proyecto.IngSoftware;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Tu hash de la base
        String hash = "$2a$10$QdI6cVWP1Dn0l68wjqk7.e0YkNqtHl5p5Kb7c.wbppcdTdWdylbEG";
        // Contraseña que usas al logearte
        String rawPassword = "admin";

        System.out.println("Coincide?: " + encoder.matches(rawPassword, hash));
    }
}

