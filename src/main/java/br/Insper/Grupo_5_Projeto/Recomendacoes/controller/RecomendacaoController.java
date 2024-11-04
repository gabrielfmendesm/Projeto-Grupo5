package br.Insper.Grupo_5_Projeto.Recomendacoes.controller;

import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.RecomendacoesUsuario;
import br.Insper.Grupo_5_Projeto.Recomendacoes.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @PostMapping
    public ResponseEntity<?> criarRecomendacao() {
        Recomendacao recomendacao = recomendacaoService.criarRecomendacaoAutomatica();

        if (recomendacao == null) {
            return ResponseEntity.badRequest().body("Nenhuma nova recomendação disponível.");
        }

        return ResponseEntity.ok(recomendacao);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obterRecomendacoes(@PathVariable String email) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacaoService.obterRecomendacoesPorEmail(email);

        if (recomendacoesUsuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recomendacoesUsuario);
    }
}