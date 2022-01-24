
package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {


    @Autowired //Esta dependencia vai ser automaticamento instanciada pelo spring pelo mecanismo de dependencia
    private ClienteRepository repo;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public ClienteService() {
    }

    public Optional<Cliente> find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
                                            + ", Tipo:" + Cliente.class.getName());
        }
        return obj;
    }
    @Transactional
    public Cliente Insert(Cliente obj){
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());

        return obj;
    }

    public Cliente update(Cliente obj) {
       Optional<Cliente> newObj = find(obj.getId());
        
        //updateData(newObj, obj);

        find(obj.getId());
        return  repo.save(obj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        repo.deleteById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }
    //Retorno de paginação de dados
    public Page<Cliente> findPage(Integer page, Integer linesForPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesForPages, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }
    public Cliente fromDTO(ClienteDTO objDTO) {
        //throw  new UnsupportedOperationException();
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);

    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
        Cidade cid = new Cidade(objDTO.getCidadeId(),null, null);

        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);

        cli.getTelefones().add(objDTO.getTelefone1());

        if (objDTO.getTelefone2() != null){
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3() != null){
            cli.getTelefones().add(objDTO.getTelefone3());
        }
        return cli;
    }
}
