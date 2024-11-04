package br.Insper.Grupo_5_Projeto.Catalogo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document(collection = "catalogo")
public class Catalogo {
    @MongoId
    private String id;
    private String userEmail;
    private List<Filme> catalogo;
}