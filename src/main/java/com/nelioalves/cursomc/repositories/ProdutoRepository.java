package com.nelioalves.cursomc.repositories;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias ")
    Page<Produto> findDistinctByNomeCotainingAndCategoriasIn(@Param("nome")  String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

    //As duas consultas tem o mesmo efeito
    //Page<Produto> findDistinctByNomeCotainingAndCategoriasIn(@Param("nome")  String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);


}

