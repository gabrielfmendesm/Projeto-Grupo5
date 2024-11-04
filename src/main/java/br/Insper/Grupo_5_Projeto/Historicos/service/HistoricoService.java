package br.Insper.Grupo_5_Projeto.Historicos.service;

import br.Insper.Grupo_5_Projeto.Historicos.model.Historico;
import br.Insper.Grupo_5_Projeto.Historicos.repository.HistoricoRepository;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    public void adicionarAoHistorico(String email, Recomendacao novaRecomendacao) {
        Historico historico = historicoRepository.findByUserEmail(email);

        if (historico == null) {
            historico = new Historico();
            historico.setUserEmail(email);
        }

        if (historico.getHistorico_recomendacoes() == null) {
            historico.setHistorico_recomendacoes(new ArrayList<>());
        }

        historico.getHistorico_recomendacoes().add(novaRecomendacao);

        historicoRepository.save(historico);
    }

    public Historico obterHistoricoPorEmail(String email) {
        return historicoRepository.findByUserEmail(email);
    }

    public boolean apagarHistorico(String email) {
        Historico historico = historicoRepository.findByUserEmail(email);

        if (historico != null) {
            historicoRepository.delete(historico);
            return true;
        } else {
            return false;
        }
    }
}