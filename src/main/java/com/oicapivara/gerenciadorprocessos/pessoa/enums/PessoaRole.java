package com.oicapivara.gerenciadorprocessos.pessoa.enums;

public enum PessoaRole {

    ADMIN("admin"),
    CLIENTE("cliente"),
    ADVOGADO("advogado");

    private String role;

    PessoaRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
