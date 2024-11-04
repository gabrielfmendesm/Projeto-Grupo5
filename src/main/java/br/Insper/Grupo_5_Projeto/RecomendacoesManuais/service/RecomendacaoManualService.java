package br.Insper.Grupo_5_Projeto.RecomendacoesManuais.service;

import br.Insper.Grupo_5_Projeto.Historicos.service.HistoricoService;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.RecomendacoesUsuario;
import br.Insper.Grupo_5_Projeto.Recomendacoes.repository.RecomendacoesRepository;
import br.Insper.Grupo_5_Projeto.RecomendacoesManuais.model.RecomendacaoManual;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecomendacaoManualService {

    @Autowired
    private RecomendacoesRepository recomendacoesRepository;

    @Autowired
    private HistoricoService historicoService;

    public RecomendacaoManual criarRecomendacaoManual(String userEmail, List<String> filmesRecomendados) {
        RecomendacaoManual novaRecomendacaoManual = new RecomendacaoManual();
        novaRecomendacaoManual.setId(new ObjectId().toHexString());
        novaRecomendacaoManual.setTipo("Manual");
        novaRecomendacaoManual.setDataRecomendacao(LocalDateTime.now());
        novaRecomendacaoManual.setFilmesRecomendados(filmesRecomendados);

        adicionarRecomendacaoManual(userEmail, novaRecomendacaoManual);

        historicoService.adicionarAoHistorico(userEmail, novaRecomendacaoManual);

        return novaRecomendacaoManual;
    }

    public void adicionarRecomendacaoManual(String userEmail, RecomendacaoManual novaRecomendacaoManual) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(userEmail);

        if (recomendacoesUsuario == null) {
            recomendacoesUsuario = new RecomendacoesUsuario();
            recomendacoesUsuario.setUserEmail(userEmail);
        }

        if (recomendacoesUsuario.getRecomendacoes() == null) {
            recomendacoesUsuario.setRecomendacoes(new ArrayList<>());
        }

        recomendacoesUsuario.getRecomendacoes().add(novaRecomendacaoManual);

        recomendacoesRepository.save(recomendacoesUsuario);
    }

    public RecomendacaoManual editarRecomendacaoManual(String email, String recomendacaoId, List<String> filmesRecomendados) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(email);

        if (recomendacoesUsuario != null) {
            for (RecomendacaoManual recomendacao : recomendacoesUsuario.getRecomendacoesManuais()) {
                if (recomendacao.getId().equals(recomendacaoId)) {
                    recomendacao.setFilmesRecomendados(filmesRecomendados);
                    recomendacao.setDataRecomendacao(LocalDateTime.now());

                    recomendacoesRepository.save(recomendacoesUsuario);

                    historicoService.adicionarAoHistorico(email, recomendacao);

                    return recomendacao;
                }
            }
        }

        return null;
    }

    public boolean excluirRecomendacaoManual(String email, String recomendacaoId) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(email);

        if (recomendacoesUsuario != null) {
            RecomendacaoManual recomendacaoParaExcluir = null;
            for (RecomendacaoManual recomendacao : recomendacoesUsuario.getRecomendacoesManuais()) {
                if (recomendacao.getId().equals(recomendacaoId)) {
                    recomendacaoParaExcluir = recomendacao;
                    break;
                }
            }

            if (recomendacaoParaExcluir != null) {
                recomendacoesUsuario.getRecomendacoes().remove(recomendacaoParaExcluir);
                recomendacoesRepository.save(recomendacoesUsuario);

                // Opcional: Remover do histórico também (implementar se necessário)
                // historicoService.removerDoHistorico(email, recomendacaoParaExcluir.getId());

                return true;
            }
        }

        return false;
    }

     public List<RecomendacaoManual> obterRecomendacoesManuais(String email) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(email);

        if (recomendacoesUsuario != null) {
            return recomendacoesUsuario.getRecomendacoesManuais();
        }

        return new ArrayList<>();
    }
}