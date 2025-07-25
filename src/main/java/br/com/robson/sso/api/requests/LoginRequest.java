package br.com.robson.sso.api.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String senha;
}