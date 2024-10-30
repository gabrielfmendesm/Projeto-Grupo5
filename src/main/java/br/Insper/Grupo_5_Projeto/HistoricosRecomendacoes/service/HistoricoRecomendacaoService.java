package br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.service;

import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.model.HistoricoRecomendacao;
import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.repository.HistoricoRecomendacaoRepository;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoRecomendacaoService {

    @Autowired
    private HistoricoRecomendacaoRepository historicoRepository;

    public HistoricoRecomendacao adicionarAoHistorico(Recomendacao recomendacao) {
        HistoricoRecomendacao historico = new HistoricoRecomendacao();
        historico.setUserEmail(recomendacao.getUserEmail());
        historico.setFilmesRecomendados(recomendacao.getFilmesRecomendados());
        historico.setDataRecomendacao(recomendacao.getDataRecomendacao());
        return historicoRepository.save(historico);
    }

    public List<HistoricoRecomendacao> obterHistoricoPorUsuario(String userEmail) {
        return historicoRepository.findByUserEmail(userEmail);
    }

    public boolean estaHistoricoVazio(String userEmail) {
        List<HistoricoRecomendacao> historico = historicoRepository.findByUserEmail(userEmail);
        return historico.isEmpty();
    }

    public void limparHistoricoPorUsuario(String userEmail) {
        List<HistoricoRecomendacao> historico = historicoRepository.findByUserEmail(userEmail);
        historicoRepository.deleteAll(historico);
    }
}