package br.com.robson.sso.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robson.sso.api.requests.LoginRequest;
import br.com.robson.sso.api.requests.LoginResponse;
import br.com.robson.sso.repositories.UserFakeRepository;
import br.com.robson.sso.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private final UserFakeRepository usuarioRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(UserFakeRepository usuarioRepo, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return usuarioRepo.buscarPorUsername(request.getUsername())
            .filter(u -> encoder.matches(request.getSenha(), u.getSenha()))
            .map(u -> ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(u.getUsername(), u.getId()))))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}