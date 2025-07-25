package br.com.robson.sso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class DelegacaoPermissao {
    private String userId;
    private Set<Acao> acoes;   // Ex: LER, LISTAR
    private boolean delegado;  // true = foi recebido por delegação, então não pode delegar
}
