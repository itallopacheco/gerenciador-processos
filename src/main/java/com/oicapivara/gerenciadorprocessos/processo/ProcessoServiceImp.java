package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.documentos.Documento;
import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.exceptions.ProcessoCreationException;
import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRepository;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessoServiceImp implements ProcessoService{

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public ProcessoDTO create(CreateProcessoDTO dto) {
        Optional<Processo> processoOptional = processoRepository.findByNumeroProcesso(dto.getNumeroProcesso());
        if (processoOptional.isPresent()) throw new UniqueFieldException("Processo já cadastrado para o número: " + dto.getNumeroProcesso());
        Long parteId = dto.getParteId();
        Long responsavelId = dto.getResponsavelId();

        validatePessoasExist(dto);

        Pessoa parte = pessoaRepository.findById(parteId).get();
        Pessoa responsavel = pessoaRepository.findById(responsavelId).get();

        if (! responsavel.getRoles().contains(PessoaRole.ADVOGADO)) throw new ProcessoCreationException("O responsável não tem permissões de Advogado para o id: " +responsavelId);

        Processo processo = new Processo(dto.getNumeroProcesso(),parte,responsavel,dto.getTema(),dto.getValorCausa());
        processoRepository.save(processo);

        ProcessoDTO response = new ProcessoDTO(
                processo.getId()
                ,processo.getNumeroProcesso()
                , new PessoaDTO(processo.getParte())
                , new PessoaDTO(processo.getResponsavel())
                , processo.getDocumentos().stream().map(documento -> new DocumentoDTO(documento)).toList()
                , processo.getTema()
                , processo.getValorCausa()

        );

        return response;
    }

    @Override
    public ProcessoDTO getById(Long id) {
        Optional<Processo> processoOptional = processoRepository.findById(id);
        if (processoOptional.isEmpty()) throw new EntityNotFoundException("Processo não encontrado para o id: "+id);

        Processo processo = processoOptional.get();

        ProcessoDTO response = new ProcessoDTO(processo);
        return response;
    }

    private void validatePessoasExist(CreateProcessoDTO dto) {
        Long parteId = dto.getParteId();
        Long responsavelId = dto.getResponsavelId();

        Boolean parteExists = pessoaRepository.existsById(parteId);
        Boolean responsavelExists = pessoaRepository.existsById(responsavelId);

        if (!parteExists && !responsavelExists) {
            throw new EntityNotFoundException("Parte não encontrada para o id: " + parteId + "\n" +
                    "Responsável não encontrado para o id: " + responsavelId);
        }
        if (!parteExists) {
            throw new EntityNotFoundException("Pessoa não encontrada para o id: " + parteId);
        }
        if (!responsavelExists) {
            throw new EntityNotFoundException("Responsável não encontrado para o id: " + responsavelId);
        }
    }


}
