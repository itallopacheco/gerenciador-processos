package com.oicapivara.gerenciadorprocessos.processo.dto;

import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDTO {

    private Long id;
    private String numeroProcesso;
    private PessoaDTO parte;
    private PessoaDTO responsavel;
    private String documentos;
    private String tema;
    private Double valorCausa;

    public ProcessoDTO(Processo processo){
        this.id = processo.getId();
        this.numeroProcesso = processo.getNumeroProcesso();
        this.parte = new PessoaDTO(processo.getParte());
        this.responsavel = new PessoaDTO(processo.getResponsavel());
        this.tema = processo.getTema();
        this.valorCausa = processo.getValorCausa();
    }

}
