package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.exceptions.LawyerCreationException;
import com.oicapivara.gerenciadorprocessos.exceptions.PasswordMatchesException;
import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.CreatePessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class PessoaServiceImpTest {


    @InjectMocks
    private PessoaServiceImp pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;

    private CreatePessoaDTO createClienteDTO;
    private PessoaDTO clienteDTO;
    private Pessoa cliente;
    private Optional<Pessoa> clienteOptional;
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
    @Test
    void testGetByIdSuccess(){

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        PessoaDTO result = pessoaService.getById(1L);
        assertNotNull(result);
        assertEquals(PessoaDTO.class,result.getClass());
        assertEquals("Itallo",result.getNome());
    }
    @Test
    void testGetByIdEntityNotFound(){
        when(pessoaRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Pessoa nao encontrada para o id: "));
        try {
            pessoaService.getById(1L);
        }catch (Exception ex){
            assertEquals(EntityNotFoundException.class, ex.getClass());
        }
    }
    @Test
    void testGetByCPFSuccess(){

        when(pessoaRepository.findByCpf(anyString())).thenReturn(cliente);
        PessoaDTO result = pessoaService.getByCpf("86206644502");
        assertNotNull(result);
        assertEquals(PessoaDTO.class,result.getClass());
        assertEquals("Itallo",result.getNome());
    }
    @Test
    void testGetByCPFEntityNotFound(){
        when(pessoaRepository.findByCpf(anyString())).thenThrow(new EntityNotFoundException("Pessoa nao encontrada para o cpf: "));
        try {
            pessoaService.getByCpf("86206644502");
        }catch (Exception ex){
            assertEquals(EntityNotFoundException.class, ex.getClass());
        }
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