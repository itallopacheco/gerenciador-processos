package com.oicapivara.gerenciadorprocessos.pessoa.dto;

import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String oab;
    private List<PessoaRole> roleList;
    public PessoaDTO(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getName();
        this.cpf = pessoa.getCpf();
        this.oab = pessoa.getOab();
        this.roleList = pessoa.getRoles();
    }

}
