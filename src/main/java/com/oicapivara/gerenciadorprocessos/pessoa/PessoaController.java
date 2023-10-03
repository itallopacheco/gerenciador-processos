package com.oicapivara.gerenciadorprocessos.pessoa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("")
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid CreatePessoaDTO dto) {
        return new ResponseEntity<PessoaDTO>(pessoaService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PessoaDTO> getById(@PathVariable(value = "id") long id) {
        PessoaDTO pessoaDTO = pessoaService.getById(id);
        return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<PessoaDTO>> getAll() {
        return new ResponseEntity<>(pessoaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PessoaDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return new ResponseEntity<>(pessoaService.search(searchTerm, page, size), HttpStatus.OK);

    }
}
