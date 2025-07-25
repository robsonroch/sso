package br.com.robson.sso.entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PermissaoRecurso {
    private String recursoId;
    private Set<Acao> acoes;
    private boolean delegado;
}
