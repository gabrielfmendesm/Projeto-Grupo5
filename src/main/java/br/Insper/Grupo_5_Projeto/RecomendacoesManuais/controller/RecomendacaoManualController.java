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

    @PostMapping("/{userEmail}")
    public ResponseEntity<RecomendacaoManual> criarRecomendacaoManual(@PathVariable String userEmail, @RequestBody List<String> filmesRecomendados) {
        RecomendacaoManual novaRecomendacao = recomendacaoManualService.criarRecomendacaoManual(userEmail, filmesRecomendados);
        return ResponseEntity.ok(novaRecomendacao);
    }

    @PutMapping("/{userEmail}/{recomendacaoId}")
    public ResponseEntity<RecomendacaoManual> editarRecomendacaoManual(@PathVariable String userEmail, @PathVariable String recomendacaoId, @RequestBody List<String> filmesRecomendados) {
        RecomendacaoManual recomendacaoAtualizada = recomendacaoManualService.editarRecomendacaoManual(userEmail, recomendacaoId, filmesRecomendados);
        if (recomendacaoAtualizada != null) {
            return ResponseEntity.ok(recomendacaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userEmail}/{recomendacaoId}")
    public ResponseEntity<String> excluirRecomendacaoManual(@PathVariable String userEmail, @PathVariable String recomendacaoId) {
        boolean excluido = recomendacaoManualService.excluirRecomendacaoManual(userEmail, recomendacaoId);
        if (excluido) {
            return ResponseEntity.ok("Recomendação excluída com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Recomendação não encontrada.");
        }
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<RecomendacaoManual>> obterRecomendacoesManuais(@PathVariable String userEmail) {
        List<RecomendacaoManual> recomendacoes = recomendacaoManualService.obterRecomendacoesManuais(userEmail);
        return ResponseEntity.ok(recomendacoes);
    }
}