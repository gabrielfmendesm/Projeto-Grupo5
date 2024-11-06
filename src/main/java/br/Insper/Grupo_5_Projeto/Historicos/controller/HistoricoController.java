package br.Insper.Grupo_5_Projeto.Historicos.controller;

import br.Insper.Grupo_5_Projeto.Historicos.model.Historico;
import br.Insper.Grupo_5_Projeto.Historicos.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping
    public ResponseEntity<?> obterHistorico(@RequestHeader("Authorization") String jwtToken) {
        Historico historico = historicoService.obterHistoricoPorEmail(jwtToken);

        if (historico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historico);

    }

    @DeleteMapping
    public ResponseEntity<?> apagarHistorico(@RequestHeader("Authorization") String jwtToken) {
        boolean removido = historicoService.apagarHistorico(jwtToken);

        if (removido) {
            return ResponseEntity.ok("Histórico apagado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Histórico não encontrado para o usuário especificado.");
        }
    }
}