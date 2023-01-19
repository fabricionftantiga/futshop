package com.futshop.futshop.Controller;

import java.util.List;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoModel> listar(){
        return produtoService.listarProdutos();
    }

    @GetMapping(path = "/promocoes")
    public List<ProdutoModel> listarPromocoes(){
        return produtoService.listarPromocoes();
    }

    @GetMapping(path = "/novidades")
    public List<ProdutoModel> listarNovidades(){
        return produtoService.listarNovidades();
    }

    @GetMapping(path = "descricao/{descricao}")
    public List<ProdutoModel> listarPesquisados(@PathVariable String descricao){
        return produtoService.buscarPorDescricao(descricao);
    }

    @GetMapping(path = "/{codigo}")
    public ProdutoModel buscarPorID(@PathVariable Long codigo){
        return produtoService.buscarProdutoPorID(codigo);
    }

    @PostMapping
    public void salvar(@RequestBody ProdutoModel produto) {
        produtoService.salvarProduto(produto);
    }

    @DeleteMapping(path = "/{codigo}")
    public void deletar(@PathVariable Long codigo){
        produtoService.deletarProdutoPorID(codigo);
    }

    @DeleteMapping()
    public void deletarTodos(){
        produtoService.deletarTodosProdutos();
    }
}
