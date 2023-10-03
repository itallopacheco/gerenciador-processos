package com.oicapivara.gerenciadorprocessos.documentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentoService {


    String upload(Long id, MultipartFile multipartFile);


}
