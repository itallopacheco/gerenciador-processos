package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRepository;
import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import com.oicapivara.gerenciadorprocessos.processo.dto.ProcessoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Optional<Pessoa> parteExists = pessoaRepository.findById(parteId);
        Optional<Pessoa> responsavelExists = pessoaRepository.findById(responsavelId);

        if (parteExists.isEmpty() && ! responsavelExists.isEmpty()){
            throw new EntityNotFoundException("Parte não encontrada para o id: " + parteId+"\n"+
                    "Responsável não encontrado para id: " + responsavelId);
        }
        if (parteExists.isEmpty()){
            throw new EntityNotFoundException("Pessoa não encontrada para o id: " + parteId);
        }
        if (responsavelExists.isEmpty()){
            throw new EntityNotFoundException("Responsável não encontrado para o id: " + responsavelId);
        }
        Pessoa parte = parteExists.get();
        Pessoa responsavel = responsavelExists.get();

        Processo processo = new Processo(dto.getNumeroProcesso(),parte,responsavel,dto.getTema(),dto.getValorCausa());
        processoRepository.save(processo);

        ProcessoDTO response = new ProcessoDTO(processo);

        return response;
    }



}
