package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.exceptions.ProcessoCreationException;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRepository;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Long parteId = dto.getParteId();
        Long responsavelId = dto.getResponsavelId();

        validatePessoasExist(dto);

        Pessoa parte = pessoaRepository.findById(parteId).get();
        Pessoa responsavel = pessoaRepository.findById(responsavelId).get();

        if (! responsavel.getRoles().contains(PessoaRole.ADVOGADO)) throw new ProcessoCreationException("O responsável não tem permissões de Advogado para o id: " +responsavelId);

        Processo processo = new Processo(dto.getNumeroProcesso(),parte,responsavel,dto.getTema(),dto.getValorCausa());
        processoRepository.save(processo);

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
