package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.exceptions.LawyerCreationException;
import com.oicapivara.gerenciadorprocessos.exceptions.PasswordMatchesException;
import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImp implements PessoaService{

    @Autowired
    PessoaRepository pessoaRepository;


    @Override
    public PessoaDTO create(CreatePessoaDTO dto) {
        if (! dto.getPassword().equals(dto.getPasswordConfirmation())) throw new PasswordMatchesException("As senhas não coincidem.");
        if (dto.getRoleList().contains(PessoaRole.ADVOGADO) &&
                ((dto.getOab() != null) && dto.getOab().isEmpty()) || (dto.getOab() == null)) {
            throw new LawyerCreationException("O campo OAB é obrigatótio para criar um usuário do tipo Advogado.");
        }
        boolean cpfExists = pessoaRepository.existsByCpf(dto.getCpf());
        if(cpfExists)throw new UniqueFieldException("cpf já cadastrado para o valor: " + dto.getCpf());

        Pessoa pessoa = dto.dtoToObject();
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }
}
