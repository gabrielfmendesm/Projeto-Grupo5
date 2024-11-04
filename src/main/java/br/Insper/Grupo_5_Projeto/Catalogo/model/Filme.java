package br.Insper.Grupo_5_Projeto.Catalogo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class Filme {
    @MongoId
    private String id;
    private String titulo;
    private String genero;
}