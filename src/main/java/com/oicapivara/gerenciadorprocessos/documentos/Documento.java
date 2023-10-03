package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.processo.Processo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String caminho;
    private String extensao;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;


}
