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
        validatePasswordMatching(dto);
        validateUniqueCpf(dto.getCpf());
        validateOabForAdvogado(dto);

        Pessoa pessoa = dto.dtoToObject();
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }



    private void validatePasswordMatching(CreatePessoaDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new PasswordMatchesException("As senhas não coincidem.");
        }
    }
    private void validateOabForAdvogado(CreatePessoaDTO dto) {
        if (dto.getRoleList().contains(PessoaRole.ADVOGADO) && (dto.getOab() == null || dto.getOab().isEmpty())) {
            throw new LawyerCreationException("O campo OAB é obrigatório para criar um usuário do tipo Advogado.");
        }
    }

    private void validateUniqueCpf(String cpf) {
        boolean cpfExists = pessoaRepository.existsByCpf(cpf);
        if (cpfExists) {
            throw new UniqueFieldException("CPF já cadastrado para o valor: " + cpf);
        }
    }


}
