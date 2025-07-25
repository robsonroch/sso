package br.com.robson.sso.repositories;

import br.com.robson.sso.entities.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FornecedorFakeRepository {

    private static final List<Fornecedor> FORNECEDORES = new ArrayList<>();

    static {
        FORNECEDORES.add(new Fornecedor(
                "f1", "12345678000100", "Fornecedor XPTO",
                List.of(new Seguimento("s1", "Limpeza", "Produtos de limpeza", TipoSeguimento.LIMPEZA)),
                List.of(new Produto("p1", "Desinfetante", "Desinfetante para piso", GranulalidadeEnum.LITRO)),
                "1", // admin é o responsável
                new ArrayList<>()
        ));
    }

    public Optional<Fornecedor> findById(String id) {
        return FORNECEDORES.stream().filter(f -> f.getFornecedorId().equals(id)).findFirst();
    }

    public List<Fornecedor> findAll() {
        return FORNECEDORES;
    }

    public void salvarDelegacao(String fornecedorId, DelegacaoPermissao delegacao) {
        findById(fornecedorId).ifPresent(f -> f.getDelegacoes().add(delegacao));
    }
}
