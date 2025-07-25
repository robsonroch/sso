package br.com.robson.sso.api;


import br.com.robson.sso.entities.User;
import br.com.robson.sso.model.LoginRequest;
import br.com.robson.sso.model.LoginResponse;
import br.com.robson.sso.repositories.UserFakeRepository;
import br.com.robson.sso.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
public class AuthApiImpl{

    private final UserFakeRepository usuarioRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;


    public ResponseEntity<LoginResponse> authLoginPost(LoginRequest request) {
        return usuarioRepo.buscarPorUsername(request.getUsername())
                .filter(u -> encoder.matches(request.getSenha(), u.getSenha()))
                .map(u -> ResponseEntity.ok(getLoginResponse(u)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private LoginResponse getLoginResponse(User user){
        var loginResponse = new LoginResponse();
        loginResponse.token(jwtUtil.generateToken(user.getUsername(), user.getId()));

        return loginResponse;
    }
}
