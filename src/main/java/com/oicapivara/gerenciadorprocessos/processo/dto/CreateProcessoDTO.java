package com.oicapivara.gerenciadorprocessos.processo.dto;

import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProcessoDTO {

    @NotEmpty
    private String numeroProcesso;
    @NotEmpty
    private Long parteId;
    @NotEmpty
    private Long responsavelId;
    @NotEmpty
    private String tema;
    @NotEmpty
    private Double valorCausa;


}
