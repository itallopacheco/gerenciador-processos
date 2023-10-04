package com.oicapivara.gerenciadorprocessos.processo.dto;

import com.oicapivara.gerenciadorprocessos.documentos.Documento;
import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDTO {

    private Long id;
    private String numeroProcesso;
    private PessoaDTO parte;
    private PessoaDTO responsavel;

    private List<DocumentoDTO> documentos = new ArrayList<>();

    private String tema;
    private Double valorCausa;
    private Boolean ativo;
    public ProcessoDTO(Processo processo){
        List<DocumentoDTO> documentoDTOS = new ArrayList<>();
        for (Documento d:processo.getDocumentos()) {
            DocumentoDTO dto = new DocumentoDTO(d);
            documentoDTOS.add(dto);
        }

        this.id = processo.getId();
        this.numeroProcesso = processo.getNumeroProcesso();
        this.parte = new PessoaDTO(processo.getParte());
        this.responsavel = new PessoaDTO(processo.getResponsavel());
        this.documentos = documentoDTOS;
        this.tema = processo.getTema();
        this.valorCausa = processo.getValorCausa();
        this.ativo = processo.getAtivo();
    }

}
