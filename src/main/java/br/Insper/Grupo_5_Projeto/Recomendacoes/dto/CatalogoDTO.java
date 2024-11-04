package br.Insper.Grupo_5_Projeto.Recomendacoes.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatalogoDTO {
    private String id;
    private String userEmail;
    private List<FilmeDTO> catalogo;
}
