package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRepository;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaServiceImp;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProcessoServiceImpTest {
    @InjectMocks
    private ProcessoServiceImp processoService;
    @Mock
    private ProcessoRepository processoRepository;
    @Mock
    private PessoaRepository pessoaRepository;


    private Pessoa cliente;
    private Pessoa advogado;
    private CreateProcessoDTO createProcessoDTO;
    private ProcessoDTO processoDTO;
    private Processo processo;
    private PessoaDTO clienteDTO;
    private PessoaDTO advogadoDTO;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        startProcessos();
    }

    @Test
    void testCreateProcessoSuccess(){
        when(processoRepository.save(any(Processo.class))).thenReturn(processo);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pessoaRepository.findById(2L)).thenReturn(Optional.of(advogado));

        ProcessoDTO result = processoService.create(createProcessoDTO);
        assertNotNull(result);
        assertEquals("123456",result.getNumeroProcesso());
        assertEquals(1L,result.getParte().getId());
        assertEquals(2L,result.getResponsavel().getId());

    }




    private void startProcessos(){
        createProcessoDTO = new CreateProcessoDTO("123456",1L,2L,"Divorcio",500.6);
        processo = new Processo(1L,"123456",cliente,advogado,"Doc","Tema",500.6);
        processoDTO = new ProcessoDTO(1L,"123456",clienteDTO,advogadoDTO,"Doc","Tema",500.6);
        cliente = new Pessoa(1L,"Itallo","86206644502",null,"senha123", List.of(PessoaRole.CLIENTE));
        advogado = new Pessoa(2L,"Itallo","86206644502","123456","senha123",List.of(PessoaRole.ADVOGADO));
        clienteDTO = new PessoaDTO(cliente);
        advogadoDTO = new PessoaDTO(advogado);
    }

}