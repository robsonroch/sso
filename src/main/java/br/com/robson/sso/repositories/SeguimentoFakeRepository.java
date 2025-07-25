package br.com.robson.sso.repositories;

import br.com.robson.sso.entities.Seguimento;
import br.com.robson.sso.entities.TipoSeguimento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeguimentoFakeRepository {

    private static final List<Seguimento> SEGUIMENTOS = List.of(
            new Seguimento("s1", "Limpeza", "Produtos de limpeza", TipoSeguimento.LIMPEZA),
            new Seguimento("s2", "Alimentício", "Produtos alimentares", TipoSeguimento.ALIMENTICIO)
    );

    public List<Seguimento> findAll() {
        return SEGUIMENTOS;
    }

    public Optional<Seguimento> findById(String id) {
        return SEGUIMENTOS.stream().filter(s -> s.getSeguimentoId().equals(id)).findFirst();
    }
}