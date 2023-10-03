package com.oicapivara.gerenciadorprocessos.processo;

import com.oicapivara.gerenciadorprocessos.documentos.Documento;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.processo.dto.CreateProcessoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "processos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroProcesso;

    @ManyToOne
    @JoinColumn(name = "parte_id")
    private Pessoa parte;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
    private List<Documento> documentos = new ArrayList<>();

    private String tema;
    private double valorCausa;

    public Processo(String numeroProcesso, Pessoa parte, Pessoa responsavel, String tema, double valorCausa) {
        this.numeroProcesso = numeroProcesso;
        this.parte = parte;
        this.responsavel = responsavel;
        this.tema = tema;
        this.valorCausa = valorCausa;
    }
}
