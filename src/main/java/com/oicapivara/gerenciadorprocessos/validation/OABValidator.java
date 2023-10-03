package com.oicapivara.gerenciadorprocessos.validation;

import com.oicapivara.gerenciadorprocessos.pessoa.CreatePessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OABValidator implements ConstraintValidator<ValidOAB, CreatePessoaDTO> {
    @Override
    public void initialize(final ValidOAB constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreatePessoaDTO createPessoaDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(createPessoaDTO.getRoleList() != null && createPessoaDTO.getRoleList().contains(PessoaRole.ADVOGADO)){
            return createPessoaDTO.getOab() != null && !createPessoaDTO.getOab().isEmpty();
        }
        return true;
    }
}
