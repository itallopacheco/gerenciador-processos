package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.exceptions.LawyerCreationException;
import com.oicapivara.gerenciadorprocessos.exceptions.PasswordMatchesException;
import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PessoaServiceImpTest {


    @InjectMocks
    private PessoaServiceImp pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;

    private CreatePessoaDTO createClienteDTO;
    private PessoaDTO clienteDTO;
    private Pessoa cliente;
    private CreatePessoaDTO createAdvogadoDTO;
    private PessoaDTO advogadoDTO;
    private Pessoa advogado;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        startPessoas();
    }

    @Test
    void testCreateClientSuccess(){
        when(pessoaRepository.existsByCpf(anyString())).thenReturn(false);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(cliente);

        PessoaDTO result = pessoaService.create(createClienteDTO);
        assertNotNull(result);
        assertEquals("Itallo",result.getNome());
        assertEquals("86206644502",result.getCpf());
    }

    @Test
    void testCreateLawyerSuccess(){
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(advogado);

        PessoaDTO result = pessoaService.create(createAdvogadoDTO);
        assertNotNull(result);
        assertEquals("Itallo",result.getNome());
        assertEquals("123456",result.getOab());

    }

    @Test
    void testCreatePasswordMismatch(){
        CreatePessoaDTO createClienteMismatchDTO = new CreatePessoaDTO("Itallo","86206644502",null,"senha123","senha456", List.of(PessoaRole.CLIENTE));
        assertThrows(PasswordMatchesException.class,() -> pessoaService.create(createClienteMismatchDTO));
    }

    @Test
    void testCreateAdvogadoMissingOAB(){
        CreatePessoaDTO createAdvogadoMissOAB = new CreatePessoaDTO("Itallo","86206644502",null,"senha123","senha123", List.of(PessoaRole.ADVOGADO));
        assertThrows(LawyerCreationException.class, () -> pessoaService.create(createAdvogadoMissOAB));
    }

    @Test
    void testCreateAdvogadoEmptyOAB(){
        createAdvogadoDTO.setOab("");
        assertThrows(LawyerCreationException.class, () -> pessoaService.create(createAdvogadoDTO));
    }

    @Test
    void testCreateNotUniqueCPF(){
        when(pessoaRepository.existsByCpf(anyString())).thenReturn(true);
        assertThrows(UniqueFieldException.class, () -> pessoaService.create(createClienteDTO));
    }


    private void startPessoas(){
        createClienteDTO = new CreatePessoaDTO("Itallo","86206644502",null,"senha123","senha123", List.of(PessoaRole.CLIENTE));
        createAdvogadoDTO = new CreatePessoaDTO("Itallo","86206644502","123456","senha123","senha123", List.of(PessoaRole.ADVOGADO));
        cliente = new Pessoa(1L,"Itallo","86206644502",null,"senha123",List.of(PessoaRole.CLIENTE));
        advogado = new Pessoa(2L,"Itallo","86206644502","123456","senha123",List.of(PessoaRole.CLIENTE));
        clienteDTO = new PessoaDTO(cliente);
        advogadoDTO = new PessoaDTO(advogado);

    }

}