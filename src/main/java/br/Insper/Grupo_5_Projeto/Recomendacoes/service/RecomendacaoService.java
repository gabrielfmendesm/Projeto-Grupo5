package br.Insper.Grupo_5_Projeto.Recomendacoes.service;

import br.Insper.Grupo_5_Projeto.Historicos.service.HistoricoService;
import br.Insper.Grupo_5_Projeto.Recomendacoes.dto.CatalogoDTO;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.Recomendacao;
import br.Insper.Grupo_5_Projeto.Recomendacoes.model.RecomendacoesUsuario;
import br.Insper.Grupo_5_Projeto.Recomendacoes.repository.RecomendacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static br.Insper.Grupo_5_Projeto.common.TokenUtils.getEmailFromToken;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacoesRepository recomendacoesRepository;

    @Autowired
    private HistoricoService historicoService;

    public Recomendacao criarRecomendacaoAutomatica(String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<CatalogoDTO>> response = restTemplate.exchange(
                "http://3.81.36.34:8080/filmes",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CatalogoDTO>>() {}
        );

        List<CatalogoDTO> catalogoFilmes = response.getBody();

        if (catalogoFilmes == null || catalogoFilmes.isEmpty()) {
            return null;
        }

        String userEmail = getEmailFromToken(jwtToken);
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

    private void adicionarRecomendacaoAutomatica(String userEmail, Recomendacao novaRecomendacao) {
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(userEmail);

        if (recomendacoesUsuario == null) {
            recomendacoesUsuario = new RecomendacoesUsuario();
            recomendacoesUsuario.setUserEmail(userEmail);
        }

        if (recomendacoesUsuario.getRecomendacoes() == null) {
            recomendacoesUsuario.setRecomendacoes(new ArrayList<>());
        }

        recomendacoesUsuario.getRecomendacoes().add(novaRecomendacao);

        recomendacoesRepository.save(recomendacoesUsuario);
    }

    private List<String> gerarFilmesRecomendados(String userEmail, List<CatalogoDTO> catalogoFilmes) {
        Set<String> filmesJaRecomendados = new HashSet<>();
        RecomendacoesUsuario recomendacoesUsuario = recomendacoesRepository.findByUserEmail(userEmail);
        if (recomendacoesUsuario != null && recomendacoesUsuario.getRecomendacoes() != null) {
            for (Recomendacao recomendacao : recomendacoesUsuario.getRecomendacoes()) {
                filmesJaRecomendados.addAll(recomendacao.getFilmesRecomendados());
            }
        }

        List<String> filmesNaoRecomendados = catalogoFilmes.stream()
                .map(CatalogoDTO::getId)
                .filter(filmeId -> !filmesJaRecomendados.contains(filmeId))
                .collect(Collectors.toList());

        return filmesNaoRecomendados.stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    public RecomendacoesUsuario obterRecomendacoesPorEmail(String jwtToken) {
        String userEmail = getEmailFromToken(jwtToken);
        return recomendacoesRepository.findByUserEmail(userEmail);
    }
}