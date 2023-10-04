package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.documentos.dto.UpdateDocumentoDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaService;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("documentos")
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @Autowired
    PessoaService pessoaService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam Long id, @RequestParam("file")MultipartFile file, Authentication authentication){
        String cpf = authentication.getName();
        Long userId = pessoaService.getByCpf(cpf).getId();
        return new ResponseEntity<>(documentoService.create(id,file,userId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getById(@PathVariable Long id){
        Resource resource = documentoService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @PatchMapping("{id}")
    public ResponseEntity<DocumentoDTO> update(@PathVariable Long id, @RequestBody UpdateDocumentoDTO dto){
        return new ResponseEntity<>(documentoService.update(id,dto),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        documentoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
