package com.oicapivara.gerenciadorprocessos.pessoa;


import com.oicapivara.gerenciadorprocessos.pessoa.dto.AuthenticationDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.CreatePessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.UpdatePessoaDTO;
import org.springframework.data.domain.Page;

public interface PessoaService {

    String login (AuthenticationDTO authenticationDTO);

    PessoaDTO create (CreatePessoaDTO createPessoaDTO);

    PessoaDTO getById (Long id);

    PessoaDTO getByCpf (String cpf);

    Page<PessoaDTO> getAll();

    Page<PessoaDTO> search(String searchTerm, Integer page, Integer size);

    PessoaDTO update (Long id, UpdatePessoaDTO dto);

    Void delete (Long id);
}
