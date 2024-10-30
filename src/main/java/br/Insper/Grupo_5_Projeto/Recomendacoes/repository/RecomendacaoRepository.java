package br.Insper.Grupo_5_Projeto.Recomendacoes.repository;

import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacaoRepository extends MongoRepository<Recomendacao, String> {
}
