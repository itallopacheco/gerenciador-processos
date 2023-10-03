package com.oicapivara.gerenciadorprocessos.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    UserDetails findUserDetailsById(Long id);
    Boolean existsByCpf(String cpf);
    Boolean existsByOab(String oab);

    Optional<Pessoa> findByCpf(String cpf);

}
