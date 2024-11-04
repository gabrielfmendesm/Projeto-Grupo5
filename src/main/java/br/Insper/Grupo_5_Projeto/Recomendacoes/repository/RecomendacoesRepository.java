package br.Insper.Grupo_5_Projeto.Recomendacoes.repository;

import br.Insper.Grupo_5_Projeto.Recomendacoes.model.RecomendacoesUsuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacoesRepository extends MongoRepository<RecomendacoesUsuario, String> {
    RecomendacoesUsuario findByUserEmail(String userEmail);
}