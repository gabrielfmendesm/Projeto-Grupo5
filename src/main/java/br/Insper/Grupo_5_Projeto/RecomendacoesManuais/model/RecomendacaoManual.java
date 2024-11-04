package br.Insper.Grupo_5_Projeto.RecomendacoesManuais.model;

import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class RecomendacaoManual extends Recomendacao {
    @MongoId
    private String id;

    public RecomendacaoManual() {
        this.setTipo("Manual");
    }
}