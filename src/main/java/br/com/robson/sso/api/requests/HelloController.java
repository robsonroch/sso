package br.com.robson.sso.api.requests;

import br.com.robson.sso.api.ApiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController implements ApiApi {

    @Override
    @GetMapping("/hello")
    public ResponseEntity<String> apiHelloGet() {
        return ResponseEntity.ok("Olá! Você está autenticado.");
    }
}
