package br.com.robson.sso.services;


import br.com.robson.sso.entities.Acao;
import br.com.robson.sso.entities.Fornecedor;
import br.com.robson.sso.repositories.FornecedorFakeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("autorizador")
public class Autorizador {

    private final FornecedorFakeRepository fornecedorRepo;

    public Autorizador(FornecedorFakeRepository fornecedorRepo) {
        this.fornecedorRepo = fornecedorRepo;
    }

    private String getUserIdAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  (String) SecurityContextHolder.getContext().getAuthentication().getDetails();// foi setado no filtro
    }

    public boolean permite(String fornecedorId, String acaoStr) {
        Acao acao = Acao.valueOf(acaoStr.toUpperCase());
        String userId = getUserIdAutenticado();

        Fornecedor fornecedor = fornecedorRepo.findById(fornecedorId).orElse(null);
        if (fornecedor == null) return false;

        // Se for o responsável direto, tem permissão total
        if (fornecedor.getResponsavelUserId().equals(userId)) return true;

        // Se houver delegação com essa ação
        return fornecedor.getDelegacoes().stream()
                .filter(d -> d.getUserId().equals(userId))
                .anyMatch(d -> d.getAcoes().contains(acao));
    }

    public boolean podeDelegar(String fornecedorId, String acaoStr) {
        Acao acao = Acao.valueOf(acaoStr.toUpperCase());
        String userId = getUserIdAutenticado();

        Fornecedor fornecedor = fornecedorRepo.findById(fornecedorId).orElse(null);
        if (fornecedor == null) return false;

        // Somente responsável direto ou delegação não-delegada pode delegar
        if (fornecedor.getResponsavelUserId().equals(userId)) return true;

        return fornecedor.getDelegacoes().stream()
                .filter(d -> d.getUserId().equals(userId))
                .filter(d -> !d.isDelegado()) // não pode redelegar
                .anyMatch(d -> d.getAcoes().contains(acao));
    }
}