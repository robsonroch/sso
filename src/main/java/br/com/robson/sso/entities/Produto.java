package br.com.robson.sso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Produto {
    private String produtoId;
    private String nome;
    private String descricao;
    private GranulalidadeEnum granulalidade;
}
