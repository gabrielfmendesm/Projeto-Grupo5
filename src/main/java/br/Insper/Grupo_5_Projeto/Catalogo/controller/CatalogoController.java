package br.Insper.Grupo_5_Projeto.Catalogo.controller;

import br.Insper.Grupo_5_Projeto.Catalogo.model.Catalogo;
import br.Insper.Grupo_5_Projeto.Catalogo.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoRepository catalogoRepository;

    @GetMapping
    public Catalogo obterCatalogo() {
        return catalogoRepository.findAll().get(0);
    }

    @PostMapping
    public Catalogo adicionarCatalogo(@RequestBody Catalogo catalogo) {
        return catalogoRepository.save(catalogo);
    }
}