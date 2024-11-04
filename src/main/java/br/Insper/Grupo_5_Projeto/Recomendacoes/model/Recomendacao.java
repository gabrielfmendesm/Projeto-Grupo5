package br.Insper.Grupo_5_Projeto.Recomendacoes.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Recomendacao {
    private String tipo;
    private LocalDateTime dataRecomendacao;
    private List<String> filmesRecomendados;
}