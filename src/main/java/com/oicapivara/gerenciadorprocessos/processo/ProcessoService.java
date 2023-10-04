package com.oicapivara.gerenciadorprocessos.processo;


import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;

public interface ProcessoService {

    ProcessoDTO create(CreateProcessoDTO dto);

    ProcessoDTO getById(Long id);




}
