package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("processo")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping("")
    public ResponseEntity<ProcessoDTO> create(@RequestBody CreateProcessoDTO dto){
        return new ResponseEntity<ProcessoDTO>(processoService.create(dto), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProcessoDTO> getById(@PathVariable(name = "id")Long id){
        return new ResponseEntity<ProcessoDTO>(processoService.getById(id),HttpStatus.OK);
    }



}
