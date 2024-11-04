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

    @GetMapping("/{email}")
    public ResponseEntity<?> obterHistorico(@PathVariable String email) {
        Historico historico = historicoService.obterHistoricoPorEmail(email);

        if (historico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historico);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> apagarHistorico(@PathVariable String email) {
        boolean removido = historicoService.apagarHistorico(email);

        if (removido) {
            return ResponseEntity.ok("Histórico apagado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Histórico não encontrado para o usuário especificado.");
        }
    }
}