package com.nelioalves.cursomc.resources;


import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.services.ClienteService;
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
@RequestMapping(value="/clientes")
public class ClienteResource {

    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring
    private ClienteService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Optional<Cliente>> find(@PathVariable Integer id) {
        Optional<Cliente> obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Insert(@Valid @RequestBody ClienteNewDTO objDTO){
        Cliente obj = service.fromDTO(objDTO);
        obj = service.Insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDTO);
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
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        //apenas um linha converter uma lista para outra lista.
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    //http://localhost:8080/clientes/page?linesForPages=3&page=0&direction=DESC
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesForPages", defaultValue = "24") Integer linesForPages,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> list = service.findPage(page, linesForPages, orderBy, direction);
        //apenas um linha converter uma lista para outra lista.
        Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
