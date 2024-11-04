package br.Insper.Grupo_5_Projeto.Recomendacoes.service;

import br.Insper.Grupo_5_Projeto.Historicos.service.HistoricoService;
import br.Insper.Grupo_5_Projeto.Recomendacoes.dto.CatalogoDTO;
import br.Insper.Grupo_5_Projeto.Recomendacoes.dto.FilmeDTO;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.RecomendacoesUsuario;
import br.Insper.Grupo_5_Projeto.Recomendacoes.repository.RecomendacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacoesRepository recomendacoesRepository;

    @Autowired
    private HistoricoService historicoService;

    public Recomendacao criarRecomendacaoAutomatica() {
        CatalogoDTO catalogoDTO = obterCatalogo();

        if (catalogoDTO == null) {
            return null;
        }

        String userEmail = catalogoDTO.getUserEmail();
        List<FilmeDTO> catalogoFilmes = catalogoDTO.getCatalogo();

        List<String> filmesRecomendados = gerarFilmesRecomendados(userEmail, catalogoFilmes);

        if (filmesRecomendados.isEmpty()) {
            return null;
        }

        Recomendacao novaRecomendacao = new Recomendacao();
        novaRecomendacao.setTipo("Automática");
        novaRecomendacao.setDataRecomendacao(LocalDateTime.now());
        novaRecomendacao.setFilmesRecomendados(filmesRecomendados);

        adicionarRecomendacaoAutomatica(userEmail, novaRecomendacao);

        historicoService.adicionarAoHistorico(userEmail, novaRecomendacao);

        return novaRecomendacao;
    }

    private CatalogoDTO obterCatalogo() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://3.81.161.81:8081/catalogo";
        try {
            CatalogoDTO catalogoDTO = restTemplate.getForObject(url, CatalogoDTO.class);
            return catalogoDTO;
        } catch (Exception e) {
            return null;
        }
    }

    private void adicionarRecomendacaoAutomatica(String email, Recomendacao novaRecomendacao) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(email);

        if (recomendacoesUsuario == null) {
            recomendacoesUsuario = new RecomendacoesUsuario();
            recomendacoesUsuario.setUserEmail(email);
        }

        if (recomendacoesUsuario.getRecomendacoes() == null) {
            recomendacoesUsuario.setRecomendacoes(new ArrayList<>());
        }

        recomendacoesUsuario.getRecomendacoes().add(novaRecomendacao);

        recomendacoesRepository.save(recomendacoesUsuario);
    }

    private List<String> gerarFilmesRecomendados(String userEmail, List<FilmeDTO> catalogoFilmes) {
        Set<String> filmesJaRecomendados = new HashSet<>();
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(userEmail);
        if (recomendacoesUsuario != null && recomendacoesUsuario.getRecomendacoes() != null) {
            for (Recomendacao recomendacao : recomendacoesUsuario.getRecomendacoes()) {
                filmesJaRecomendados.addAll(recomendacao.getFilmesRecomendados());
            }
        }

        List<String> filmesNaoRecomendados = catalogoFilmes.stream()
                .map(FilmeDTO::getId)
                .filter(filmeId -> !filmesJaRecomendados.contains(filmeId))
                .collect(Collectors.toList());

        List<String> filmesRecomendados = filmesNaoRecomendados.stream()
                .limit(3)
                .collect(Collectors.toList());

        return filmesRecomendados;
    }

    public RecomendacoesUsuario obterRecomendacoesPorEmail(String email) {
        return recomendacoesRepository.findByUserEmail(email);
    }
}