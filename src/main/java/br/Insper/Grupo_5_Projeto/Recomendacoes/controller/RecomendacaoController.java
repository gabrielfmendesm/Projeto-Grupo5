package br.Insper.Grupo_5_Projeto.Recomendacoes.controller;

import br.Insper.Grupo_5_Projeto.Recomendacoes.dto.CatalogoDTO;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @PostMapping
    public ResponseEntity<Recomendacao> criarRecomendacao(@RequestBody CatalogoDTO catalogoDTO) {

        String email = catalogoDTO.getEmail();
        Recomendacao recomendacao = recomendacaoService.gerarRecomendacaoParaUsuario(email, catalogoDTO.getFilmes());
        return new ResponseEntity<>(recomendacao, HttpStatus.CREATED);
    }
}
