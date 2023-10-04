package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.documentos.dto.UpdateDocumentoDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentoService {


    String create(Long id, MultipartFile multipartFile, Long userId);

    Resource getById(Long id);

    DocumentoDTO update(Long id, UpdateDocumentoDTO dto);

    void delete(Long id);


}
