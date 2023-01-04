package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping
    public UsuarioModel salvarUsuario(@RequestBody UsuarioModel usuario){
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @GetMapping(path = "/produtos/usuario/{codigo}")
    public List<CarrinhoModel> listarProdutosDeUmUsuario(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);
        List<CarrinhoModel> lista = new ArrayList<CarrinhoModel>();

        for(CarrinhoModel produtos: usuario.getItens()){
            lista.add(produtos);
        }

        return lista;
    }

    @DeleteMapping
    public void excluirTodosUsuarios(){
        List<UsuarioModel> usuario = usuarioRepository.findAll();

        for(UsuarioModel user: usuario){
            for(CarrinhoModel item: user.getItens()){
                ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
                prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
                produtoRepository.save(prod);
            }
        }

        usuarioRepository.deleteAll();
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public void excluirUsuario(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel item: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(prod);
        }

        usuarioRepository.deleteById(codigo);
    }
}
