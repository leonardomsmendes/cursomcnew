package com.nelioalves.cursomc.resources;
import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring
    private CategoriaService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Optional<Categoria> obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Insert(@Valid @RequestBody CategoriaDTO objDTO){
        Categoria obj = service.fromDTO(objDTO);
        obj = service.Insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
        Categoria obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        //apenas um linha converter uma lista para outra lista.
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    //http://localhost:8080/categorias/page?linesForPages=3&page=0&direction=DESC
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesForPages", defaultValue = "24") Integer linesForPages,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> list = service.findPage(page, linesForPages, orderBy, direction);
        //apenas um linha converter uma lista para outra lista.
        Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
