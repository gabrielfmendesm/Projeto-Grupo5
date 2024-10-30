package br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.controller;

import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.model.HistoricoRecomendacao;
import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.service.HistoricoRecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoRecomendacaoController {

    @Autowired
    private HistoricoRecomendacaoService historicoService;

    @GetMapping("/{email}")
    public ResponseEntity<List<HistoricoRecomendacao>> obterHistorico(@PathVariable String email) {
        List<HistoricoRecomendacao> historico = historicoService.obterHistoricoPorUsuario(email);
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> limparHistorico(@PathVariable String email) {
        if (historicoService.estaHistoricoVazio(email)) {
            return new ResponseEntity<>("O histórico já está vazio.", HttpStatus.OK);
        }

        historicoService.limparHistoricoPorUsuario(email);
        return new ResponseEntity<>("Histórico Excluído com sucesso.", HttpStatus.OK);
    }
}
