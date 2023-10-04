package com.oicapivara.gerenciadorprocessos.processo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo,Long> {

    Optional<Processo> findByNumeroProcesso(String numeroProcesso);
}
