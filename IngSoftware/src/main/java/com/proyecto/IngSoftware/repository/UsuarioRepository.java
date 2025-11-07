
package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método clave para Spring Security: buscar por nombre de usuario (username)
    Optional<Usuario> findByUsername(String username);
}