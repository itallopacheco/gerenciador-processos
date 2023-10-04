package com.oicapivara.gerenciadorprocessos.processo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProcessoDTO {

    private String tema;
    private Double valorCausa;
    private Boolean ativo;
}
