// Archivo: src/main/java/com/proyecto/IngSoftware/service/CustomUserDetailsService.java

package com.proyecto.IngSoftware.service;

import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    /**
 * Carga los detalles del usuario desde la base de datos para la autenticación de Spring Security.
 */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // 2. Mapear el rol del usuario a una GrantedAuthority de Spring Security
        // Se añade el prefijo "ROLE_" que es la convención de Spring Security
        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toUpperCase())
        );

        // 3. Devolver un objeto UserDetails de Spring Security
        return new User(usuario.getUsername(), usuario.getPasswordHash(), authorities);
    }
}