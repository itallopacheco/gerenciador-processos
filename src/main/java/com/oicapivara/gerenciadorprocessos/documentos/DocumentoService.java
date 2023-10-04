package com.oicapivara.gerenciadorprocessos.documentos;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentoService {


    String create(Long id, MultipartFile multipartFile);

    Resource getById(Long id);



}
