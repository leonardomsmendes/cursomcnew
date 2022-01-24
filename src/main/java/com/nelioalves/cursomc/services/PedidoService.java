
package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {


    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring pelo mecanismo de dependencia
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Optional<Pedido> find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
                                            + ", Tipo:" + Pedido.class.getName());
        }
        return obj;


    }

    @Transactional
    public Pedido Insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preecherPagamentoComBoleto(pagto,obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip: obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).get().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());

        return obj;
    }
}
