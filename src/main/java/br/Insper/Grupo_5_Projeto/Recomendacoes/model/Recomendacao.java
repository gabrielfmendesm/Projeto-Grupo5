package br.Insper.Grupo_5_Projeto.Recomendacoes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recomendacoes")
@Getter
@Setter
public class Recomendacao {
    @MongoId
    private String id;
    private String userEmail;
    private List<String> filmesRecomendados;
    private LocalDateTime dataRecomendacao;
}
