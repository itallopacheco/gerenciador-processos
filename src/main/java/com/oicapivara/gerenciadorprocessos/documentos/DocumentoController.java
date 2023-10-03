package com.oicapivara.gerenciadorprocessos.documentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("documentos")
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam Long id,@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(documentoService.upload(id,file), HttpStatus.OK);
    }
}
