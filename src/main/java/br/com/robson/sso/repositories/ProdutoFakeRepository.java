package br.com.robson.sso.repositories;

import br.com.robson.sso.entities.GranulalidadeEnum;
import br.com.robson.sso.entities.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProdutoFakeRepository {

    private static final List<Produto> PRODUTOS = List.of(
            new Produto("p1", "Desinfetante", "Desinfetante para piso", GranulalidadeEnum.LITRO),
            new Produto("p2", "Sabão", "Sabão em pó", GranulalidadeEnum.GRAMA)
    );

    public List<Produto> findAll() {
        return PRODUTOS;
    }

    public Optional<Produto> findById(String id) {
        return PRODUTOS.stream().filter(p -> p.getProdutoId().equals(id)).findFirst();
    }
}