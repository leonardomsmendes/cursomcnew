package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring
    private PedidoService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Optional<Pedido> obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    //Posso criar um DTO contendo todos os campos ou usar todas a classes envolvidas
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Insert(@Valid @RequestBody Pedido obj){

        obj = service.Insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
