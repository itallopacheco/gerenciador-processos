package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImp implements PessoaService{

    @Autowired
    PessoaRepository pessoaRepository;


    @Override
    public PessoaDTO create(CreatePessoaDTO dto) {
        boolean cpfExists = pessoaRepository.existsByCpf(dto.getCpf());
        if(cpfExists)throw new UniqueFieldException("cpf já cadastrado para o valor: " + dto.getCpf());

        Pessoa pessoa = dto.dtoToObject();
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }
}
