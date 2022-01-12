
package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {


    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring pelo mecanismo de dependencia
    private PedidoRepository repo;

    public Optional<Pedido> find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
                                            + ", Tipo:" + Pedido.class.getName());
        }
        return obj;


    }
}
