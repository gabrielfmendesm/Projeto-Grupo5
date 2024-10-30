package br.Insper.Grupo_5_Projeto.Recomendacoes.service;

import br.Insper.Grupo_5_Projeto.HistoricosRecomendacoes.service.HistoricoRecomendacaoService;
import br.Insper.Grupo_5_Projeto.Recomendacoes.dto.FilmeDTO;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.repository.RecomendacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private HistoricoRecomendacaoService historicoService;

    public Recomendacao gerarRecomendacaoParaUsuario(String userEmail, List<FilmeDTO> catalogoFilmes) {
        if (catalogoFilmes == null || catalogoFilmes.isEmpty()) {
            throw new IllegalArgumentException("O catálogo de filmes está vazio.");
        }

        List<String> filmesRecomendados = catalogoFilmes.stream()
                .limit(3)
                .map(FilmeDTO::getId)
                .collect(Collectors.toList());

        Recomendacao recomendacao = new Recomendacao();
        recomendacao.setUserEmail(userEmail);
        recomendacao.setFilmesRecomendados(filmesRecomendados);
        recomendacao.setDataRecomendacao(LocalDateTime.now());

        Recomendacao salva = recomendacaoRepository.save(recomendacao);

        historicoService.adicionarAoHistorico(salva);

        return salva;
    }
}
