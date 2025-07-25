package br.com.robson.sso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Seguimento {
    private String seguimentoId;
    private String nome;
    private String descricao;
    private TipoSeguimento tipo;
}
