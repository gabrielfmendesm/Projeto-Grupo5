package br.Insper.Grupo_5_Projeto.Recomendacoes.model;

import br.Insper.Grupo_5_Projeto.RecomendacoesManuais.model.RecomendacaoManual;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "recomendacoes")
@Getter
@Setter
public class RecomendacoesUsuario {
    @MongoId
    private String id;
    private String userEmail;
    private List<Recomendacao> recomendacoes;

    @JsonIgnore
    public List<RecomendacaoManual> getRecomendacoesManuais() {
        return recomendacoes.stream()
                .filter(recomendacao -> "Manual".equals(recomendacao.getTipo()) && recomendacao instanceof RecomendacaoManual)
                .map(recomendacao -> (RecomendacaoManual) recomendacao)
                .collect(Collectors.toList());
    }
}