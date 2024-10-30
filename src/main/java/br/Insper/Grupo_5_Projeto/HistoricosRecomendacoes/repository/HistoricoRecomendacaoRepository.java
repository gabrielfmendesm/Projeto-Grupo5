package br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.repository;

import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.model.HistoricoRecomendacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRecomendacaoRepository extends MongoRepository<HistoricoRecomendacao, String> {
    List<HistoricoRecomendacao> findByUserEmail(String userEmail);
}