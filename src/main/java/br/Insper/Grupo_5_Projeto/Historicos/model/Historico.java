package br.Insper.Grupo_5_Projeto.Historicos.model;

import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "historicos")
@Getter
@Setter
public class Historico {
    @MongoId
    private String id;
    private String userEmail;
    private List<Recomendacao> historico_recomendacoes;
}
