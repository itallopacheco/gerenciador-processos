package com.oicapivara.gerenciadorprocessos.pessoa;


public interface PessoaService {

    PessoaDTO create (CreatePessoaDTO createPessoaDTO);

    PessoaDTO getById (Long id);

    PessoaDTO getByCpf (String cpf);

}
