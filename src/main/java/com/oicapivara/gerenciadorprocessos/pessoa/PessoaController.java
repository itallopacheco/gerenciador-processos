package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.pessoa.dto.*;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO dto){
        try{
            return new ResponseEntity<>(new LoginResponseDTO(pessoaService.login(dto)),HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @PatchMapping("{id}")
    public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @RequestBody UpdatePessoaDTO dto){
        return new ResponseEntity<>(pessoaService.update(id,dto),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return new ResponseEntity<>(pessoaService.delete(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return new ResponseEntity<>("pong",HttpStatus.OK);
    }
}
