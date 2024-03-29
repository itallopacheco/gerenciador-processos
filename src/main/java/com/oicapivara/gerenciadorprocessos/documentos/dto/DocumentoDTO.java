package com.oicapivara.gerenciadorprocessos.documentos.dto;

import com.oicapivara.gerenciadorprocessos.documentos.Documento;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO {

    private Long id;

    private String nome;
    private String extensao;
    private String caminho;
    private Boolean ativo;

    private Long proprietarioId;
    private Long processoId;

    public DocumentoDTO(Documento documento){
        this.id = documento.getId();
        this.nome = documento.getNome();
        this.extensao = documento.getExtensao();
        this.processoId = documento.getProcesso().getId();
        this.caminho = documento.getCaminho();
        this.ativo = documento.getAtivo();
        this.proprietarioId = documento.getProprietario().getId();
    }

}
