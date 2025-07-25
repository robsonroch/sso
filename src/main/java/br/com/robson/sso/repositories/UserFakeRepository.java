package br.com.robson.sso.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.robson.sso.entities.User;

@Component
public class UserFakeRepository {

    private static final List<User> USUARIOS = List.of(
            new User("1", "admin", new BCryptPasswordEncoder().encode("123456"), List.of("ROLE_ADMIN")),
            new User("2", "user", new BCryptPasswordEncoder().encode("senha"), List.of("ROLE_USER")),
            new User("3", "chefe", new BCryptPasswordEncoder().encode("1234"), List.of("ROLE_USER"))
    );

    public Optional<User> buscarPorUsername(String username) {
        return USUARIOS.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public Optional<User> buscarPorId(String userId) {
        return USUARIOS.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }

    public List<User> listarTodos() {
        return USUARIOS;
    }
}