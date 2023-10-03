package com.oicapivara.gerenciadorprocessos.pessoa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("")
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid CreatePessoaDTO dto){
        return new ResponseEntity<PessoaDTO>(pessoaService.create(dto), HttpStatus.CREATED);
    }

}
