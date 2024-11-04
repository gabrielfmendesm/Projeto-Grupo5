package br.Insper.Grupo_5_Projeto.Catalogo.repository;

import br.Insper.Grupo_5_Projeto.Catalogo.model.Catalogo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogoRepository extends MongoRepository<Catalogo, String> {
    Catalogo findByUserEmail(String userEmail);
}
