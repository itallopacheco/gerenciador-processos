package com.oicapivara.gerenciadorprocessos.documentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("documentos")
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam Long id,@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(documentoService.create(id,file), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getById(@PathVariable Long id){
        Resource resource = documentoService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
