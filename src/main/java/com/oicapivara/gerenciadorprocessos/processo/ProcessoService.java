package com.oicapivara.gerenciadorprocessos.processo;


import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.UpdateProcessoDTO;

public interface ProcessoService {

    ProcessoDTO create(CreateProcessoDTO dto);

    ProcessoDTO getById(Long id);

    ProcessoDTO update(Long  id, UpdateProcessoDTO dto);


}
