package br.com.robson.sso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Fornecedor {
    private String fornecedorId;
    private String cnpj;
    private String nome;
    private List<Seguimento> seguimentos;
    private List<Produto> produtos;

    private String responsavelUserId;         // Dono direto
    private List<DelegacaoPermissao> delegacoes; // Permissões delegadas
}
