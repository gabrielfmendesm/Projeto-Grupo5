package br.Insper.Grupo_5_Projeto.RecomendacoesManuais.controller;

import br.Insper.Grupo_5_Projeto.RecomendacoesManuais.model.RecomendacaoManual;
import br.Insper.Grupo_5_Projeto.RecomendacoesManuais.service.RecomendacaoManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes/manuais")
public class RecomendacaoManualController {

    @Autowired
    private RecomendacaoManualService recomendacaoManualService;

    @PostMapping
    public ResponseEntity<RecomendacaoManual> criarRecomendacaoManual(
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody List<String> filmesRecomendados) {
        RecomendacaoManual novaRecomendacao = recomendacaoManualService.criarRecomendacaoManual(jwtToken, filmesRecomendados);
        return ResponseEntity.ok(novaRecomendacao);
    }

    @PutMapping("/{recomendacaoId}")
    public ResponseEntity<RecomendacaoManual> editarRecomendacaoManual(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String recomendacaoId,
            @RequestBody List<String> filmesRecomendados) {
        RecomendacaoManual recomendacaoAtualizada = recomendacaoManualService.editarRecomendacaoManual(jwtToken, recomendacaoId, filmesRecomendados);
        if (recomendacaoAtualizada != null) {
            return ResponseEntity.ok(recomendacaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{recomendacaoId}")
    public ResponseEntity<String> excluirRecomendacaoManual(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String recomendacaoId) {
        boolean excluido = recomendacaoManualService.excluirRecomendacaoManual(jwtToken, recomendacaoId);
        if (excluido) {
            return ResponseEntity.ok("Recomendação excluída com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Recomendação não encontrada.");
        }
    }

    @GetMapping
    public ResponseEntity<List<RecomendacaoManual>> obterRecomendacoesManuais(@RequestHeader("Authorization") String jwtToken) {
        List<RecomendacaoManual> recomendacoes = recomendacaoManualService.obterRecomendacoesManuais(jwtToken);
        return ResponseEntity.ok(recomendacoes);
    }
}