package br.com.robson.sso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String senha;
    private List<String> roles; // ← usado pelo Spring Security
}