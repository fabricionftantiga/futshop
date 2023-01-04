package com.futshop.futshop.Controller;

import java.util.List;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping(value = "listarProdutos")
    public List<ProdutoModel> listar(){
        return repository.findAll();
    }

    @GetMapping(value = "buscarPromocoes")
    public List<ProdutoModel> listarPromocoes(){
        return repository.buscarPromocoes();
    }

    @GetMapping(value = "buscarNovidades")
    public List<ProdutoModel> listarNovidades(){
        return repository.buscarNovidades();
    }

    @GetMapping(value = "buscarProdutoPorID")
    public ProdutoModel buscarPorID(@RequestParam (name = "codigo") Long codigo){
        return repository.buscarPorID(codigo);
    }

    @PostMapping(value = "salvarProduto")
    public void salvar(@RequestBody ProdutoModel produto) {
        repository.save(produto);
    }

    @DeleteMapping(value = "excluirProduto")
    public void deletar(@RequestParam (name = "codigo") Long codigo){
        repository.deleteById(codigo);
    }

    @DeleteMapping(value = "deletarTodosProdutos")
    public void deletarTodos(){
        repository.deleteAll();
    }
}
