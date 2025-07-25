package br.com.robson.sso.api;

import br.com.robson.sso.entities.DelegacaoPermissao;
import br.com.robson.sso.entities.Fornecedor;
import br.com.robson.sso.repositories.FornecedorFakeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorFakeRepository fornecedorRepo;

    public FornecedorController(FornecedorFakeRepository fornecedorRepo) {
        this.fornecedorRepo = fornecedorRepo;
    }

    /**
     * Visualiza os dados de um fornecedor se o usuário tiver permissão para LER
     */
    @GetMapping("/{id}")
    @PreAuthorize("@autorizador.permite(#id, 'LER')")
    public ResponseEntity<Fornecedor> visualizar(@PathVariable String id) {
        return fornecedorRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Permite delegar uma ação sobre um fornecedor a outro usuário
     * Somente permitido se o usuário tiver permissão direta para a ação
     */
    @PostMapping("/{id}/delegar")
    @PreAuthorize("@autorizador.podeDelegar(#id, 'LER')")
    public ResponseEntity<Void> delegar(@PathVariable String id, @Valid @RequestBody DelegacaoPermissao delegacao) {
        fornecedorRepo.salvarDelegacao(id, delegacao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fornecedores/teste")
    @PreAuthorize("true")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("OK");
    }
}
