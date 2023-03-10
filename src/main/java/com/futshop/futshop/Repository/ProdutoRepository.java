package com.futshop.futshop.Repository;

import com.futshop.futshop.Model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository <ProdutoModel, Long> {

    @Query(value = "select * from produto where codigo = ?", nativeQuery = true)
    public ProdutoModel buscarPorID(Long codigo);

    @Query(value = "select * from produto order by promocao desc", nativeQuery = true)
    public List<ProdutoModel> buscarPromocoes();

    @Query(value = "select * from produto order by dataPostagem asc", nativeQuery = true)
    public List<ProdutoModel> buscarNovidades();

    @Query(value = "select * from produto  where descricao like %?%", nativeQuery = true)
    public List<ProdutoModel> buscarPorDescricao(String descricao);
}
