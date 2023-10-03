package com.oicapivara.gerenciadorprocessos.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    UserDetails findUserDetailsById(Long id);
    Boolean existsByCpf(String cpf);
    Boolean existsByOab(String oab);
}
