package com.oicapivara.gerenciadorprocessos.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    UserDetails findUserDetailsById(Long id);
    Boolean existsByCpf(String cpf);
    Boolean existsByOab(String oab);
    Optional<Pessoa> findByCpf(String cpf);

    @Query("FROM Pessoa p WHERE LOWER(p.name) like LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.cpf) like LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.oab" +
            ") like LOWER(CONCAT('%', :searchTerm, '%')) ")
    Page<Pessoa> search(@Param("searchTerm") String searchTerm, Pageable pageable);

}
