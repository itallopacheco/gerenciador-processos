package com.oicapivara.gerenciadorprocessos.pessoa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("")
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid CreatePessoaDTO dto){
        return new ResponseEntity<PessoaDTO>(pessoaService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PessoaDTO> getById(@PathVariable(value = "id")long id){
        PessoaDTO pessoaDTO = pessoaService.getById(id);
        return new ResponseEntity<>(pessoaDTO,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PessoaDTO> getByCpf(@RequestParam("cpf")String cpf){
        PessoaDTO pessoaDTO = pessoaService.getByCpf(cpf);
        return new ResponseEntity<>(pessoaDTO,HttpStatus.OK);
    }


}
