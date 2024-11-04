package br.Insper.Grupo_5_Projeto.Historicos.repository;

import br.Insper.Grupo_5_Projeto.Historicos.model.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends MongoRepository<Historico, String> {
    Historico findByUserEmail(String userEmail);
}