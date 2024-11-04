package br.Insper.Grupo_5_Projeto.RecomendacoesManuais.repository;

import br.Insper.Grupo_5_Projeto.RecomendacoesManuais.model.RecomendacaoManual;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecomendacoesManuaisRepository extends MongoRepository<RecomendacaoManual, String> {
}
