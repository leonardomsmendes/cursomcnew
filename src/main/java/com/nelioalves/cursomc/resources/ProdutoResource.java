package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.ProdutoDTO;
import com.nelioalves.cursomc.resources.utils.URL;
import com.nelioalves.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring
    private ProdutoService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Optional<Produto> obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesForPages", defaultValue = "24") Integer linesForPages,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecoder = URL.decodeParam(nome);
        Page<Produto> list = service.search(nomeDecoder, ids, page, linesForPages, orderBy, direction);
        //apenas um linha converter uma lista para outra lista.
        Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
