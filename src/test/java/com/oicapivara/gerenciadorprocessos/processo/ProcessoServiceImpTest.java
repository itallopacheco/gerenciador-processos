package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.documentos.Documento;
import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.exceptions.ProcessoCreationException;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProcessoServiceImpTest {
    @InjectMocks
    private ProcessoServiceImp processoService;
    @Mock
    private ProcessoRepository processoRepository;
    @Mock
    private PessoaRepository pessoaRepository;


    private List<Documento> documentos = new ArrayList<>();
    private List<DocumentoDTO> documentoDTOS = new ArrayList<>();
    private Pessoa cliente;
    private Pessoa advogado;
    private CreateProcessoDTO createProcessoDTO;
    private ProcessoDTO processoDTO;
    private Processo processo;
    private Processo processoCreated;
    private PessoaDTO clienteDTO;
    private PessoaDTO advogadoDTO;

    private Documento documento;
    private DocumentoDTO documentoDTO;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        startProcessos();
    }

    @Test
    void testCreateProcessoSuccess(){
        when(processoRepository.save(any(Processo.class))).thenReturn(processoCreated);
        when(pessoaRepository.existsById(anyLong())).thenReturn(true);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pessoaRepository.findById(2L)).thenReturn(Optional.of(advogado));

        ProcessoDTO result = processoService.create(createProcessoDTO);
        assertNotNull(result);
        assertEquals("123456",result.getNumeroProcesso());
        assertEquals(1L,result.getParte().getId());
        assertEquals(2L,result.getResponsavel().getId());

    }
    @Test
    void testCreateProcessoWithoutParte(){
        when(pessoaRepository.existsById(1L)).thenReturn(false);
        when(pessoaRepository.existsById(2L)).thenReturn(true);
        when(pessoaRepository.findById(1L)).thenThrow(new EntityNotFoundException("Pessoa não encontrada para o id: "));
        when(pessoaRepository.findById(2L)).thenReturn(Optional.of(advogado));

        try{
            processoService.create(createProcessoDTO);
        } catch (Exception e){
            assertEquals(EntityNotFoundException.class,e.getClass());
            assertEquals("Pessoa não encontrada para o id: 1",e.getMessage());
        }
    }
    @Test
    void testCreateProcessoWithoutAdvogado(){
        when(pessoaRepository.existsById(1L)).thenReturn(true);
        when(pessoaRepository.existsById(2L)).thenReturn(false);
        when(pessoaRepository.findById(2L)).thenThrow(new EntityNotFoundException("Responsável não encontrado para o id: "));
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));

        try{
            processoService.create(createProcessoDTO);
        } catch (Exception e){
            assertEquals(EntityNotFoundException.class,e.getClass());
            assertEquals("Responsável não encontrado para o id: 2",e.getMessage());
        }
    }
    @Test
    void testCreateProcessoWithoutPessoas(){
        when(pessoaRepository.existsById(anyLong())).thenReturn(false);

        try{
            processoService.create(createProcessoDTO);
        } catch (Exception e){
            assertEquals(EntityNotFoundException.class,e.getClass());
            assertEquals("Parte não encontrada para o id: 1\n" +
                    "Responsável não encontrado para o id: 2",e.getMessage());
        }
    }
    @Test
    void testCreateProcessoWithResponsavelNotAdvogado(){
        when(pessoaRepository.existsById(anyLong())).thenReturn(true);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pessoaRepository.findById(2L)).thenReturn(Optional.of(advogado));
        try{
            createProcessoDTO.setResponsavelId(1L);
            processoService.create(createProcessoDTO);
        } catch (Exception e){
            assertEquals(ProcessoCreationException.class, e.getClass());
            assertEquals("O responsável não tem permissões de Advogado para o id: 1",e.getMessage());
        }
    }

    @Test
    void testGetProcessoByIdSuccess(){
        when(processoRepository.findById(anyLong())).thenReturn(Optional.of(processo));
        ProcessoDTO response = processoService.getById(1L);
        assertEquals(ProcessoDTO.class,response.getClass());
        assertEquals("123456",response.getNumeroProcesso());
        assertEquals(1,response.getDocumentos().size());
    }

    private void startProcessos(){
        createProcessoDTO = new CreateProcessoDTO("123456",1L,2L,"Divorcio",500.6);
        cliente = new Pessoa(1L,"Itallo","86206644502",null,"senha123", List.of(PessoaRole.CLIENTE));
        advogado = new Pessoa(2L,"Itallo","86206644502","123456","senha123",List.of(PessoaRole.ADVOGADO));
        clienteDTO = new PessoaDTO(cliente);
        advogadoDTO = new PessoaDTO(advogado);

        processo = new Processo(1L,"123456",cliente,advogado,documentos,"Tema",500.6,true);
        processoDTO = new ProcessoDTO(1L,"123456",clienteDTO,advogadoDTO,documentoDTOS,"Tema",500.6,true);

        documento = new Documento(1L,"Documento Teste","pdf","caminho/documento.pdf",true,advogado,processo);
        documentoDTO = new DocumentoDTO(documento);
        documentos.add(documento);
        documentoDTOS.add(documentoDTO);

        processoCreated = new Processo(1L,"123456",cliente,advogado,documentos,"Tema",500.6,true);
    }

}