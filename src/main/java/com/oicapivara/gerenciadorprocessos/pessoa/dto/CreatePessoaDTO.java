package com.oicapivara.gerenciadorprocessos.pessoa.dto;

import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePessoaDTO {

    @NotEmpty(message = "O nome é obrigatório.")
    private String nome;

    @NotEmpty(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos")
    @CPF(message = "O CPF é inválido.")
    private String cpf;

    private String oab;

    @NotEmpty(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    @NotEmpty(message = "A confirmação de senha é obrigatória")
    private String passwordConfirmation;

    @NotEmpty(message = "A lista de roles não pode ser nula")
    private List<PessoaRole> roleList;

    public Pessoa dtoToObject(){
        Pessoa pessoa = new Pessoa(
                this.getNome(),
                this.getCpf(),
                this.getOab(),
                new BCryptPasswordEncoder().encode(this.getPassword()),
                this.roleList
        );
        return  pessoa;
    }
}
