
package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {


    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring pelo mecanismo de dependencia
    private CategoriaRepository repo;

    public Optional<Categoria> find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
                                            + ", Tipo:" + Categoria.class.getName());
        }
        return obj;
        //return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo: " + Categoria.class.getName()));

    }

    public Categoria Insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return  repo.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        repo.deleteById(id);
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }
    //Retorno de paginação de dados
    public Page<Categoria> findPage(Integer page, Integer linesForPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesForPages, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }
    public Categoria fromDTO(CategoriaDTO objDTO) {
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

}
