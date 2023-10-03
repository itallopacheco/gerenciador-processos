package com.oicapivara.gerenciadorprocessos.pessoa;


import org.springframework.data.domain.Page;

public interface PessoaService {

    PessoaDTO create (CreatePessoaDTO createPessoaDTO);

    PessoaDTO getById (Long id);

    PessoaDTO getByCpf (String cpf);

    Page<PessoaDTO> getAll();

    Page<PessoaDTO> search(String searchTerm, Integer page, Integer size);

}
