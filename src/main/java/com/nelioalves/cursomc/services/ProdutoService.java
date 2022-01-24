
package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {


    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring pelo mecanismo de dependencia
    private ProdutoRepository repo;
    private ProdutoService repoProdutoService;


    @Autowired
    private CategoriaRepository categoriaRepository;

    public Optional<Produto> find(Integer id) {
        Optional<Produto> obj = repoProdutoService.find(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
                                            + ", Tipo:" + Produto.class.getName());
        }
        return obj;
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesForPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesForPages, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        //return repo.search(nome, categorias, pageRequest);

        return repo.findDistinctByNomeCotainingAndCategoriasIn(nome, categorias, pageRequest);



    }
}
