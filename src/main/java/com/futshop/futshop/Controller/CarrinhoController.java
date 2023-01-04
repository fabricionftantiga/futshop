package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping(path = "/produto/{codigo}/usuario/{codigoCli}")
    public void adcionarItem(@PathVariable Long codigo,
                             @PathVariable Long codigoCli){
        CarrinhoModel itens = new CarrinhoModel();
        ProdutoModel produto = produtoRepository.buscarPorID(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        if(produto.getQuantidadeEstoque() > 0){
            int cont = 0;
            for(CarrinhoModel item: usuario.getItens()){
                if(produto.getCodigo() == item.getCodigo()){
                    soma(produto, item);
                    cont++;
                }
            }

            if(cont == 0){
                itens.setCodigo(produto.getCodigo());
                itens.setDescricaoProduto(produto.getDescricao());
                itens.setTamanho(produto.getTamanho());
                itens.setQuantidade(1);
                itens.setPrecoUnitario(produto.getValor());
                itens.setPrecoFinal(itens.getPrecoUnitario());

                usuario.setItens(itens);
            }
        }

        produtoRepository.save(produto);
        usuarioRepository.save(usuario);
    }

    @PutMapping(path = "/produto/{codigo}/usuario/{codigoCli}/acao/{acao}")
    public void alterarItem(@PathVariable Long codigo,
                            @PathVariable Long codigoCli,
                            @PathVariable String acao){
        ProdutoModel produto = produtoRepository.buscarPorID(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        for(CarrinhoModel item: usuario.getItens()){
            if(item.getCodigo() == codigo && acao.equals("+") && produto.getQuantidadeEstoque() > 0) soma(produto, item);
            if(item.getCodigo() == codigo && acao.equals("-")) subtracao(produto, item);
            break;
        }

        produtoRepository.save(produto);
        usuarioRepository.save(usuario);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public void excluirItens(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel produto: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(produto.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + produto.getQuantidade());
            produtoRepository.save(prod);
        }

        usuario.getItens().clear();
        usuarioRepository.save(usuario);
    }

    public void soma(ProdutoModel produto, CarrinhoModel item){
        item.setQuantidade(item.getQuantidade() + 1);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);

        item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
    }

    public void subtracao(ProdutoModel produto, CarrinhoModel item){
        if(item.getQuantidade() == 1){
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
            excluirItens(item.getCodigo());
        }
        else{
            item.setQuantidade(item.getQuantidade() - 1);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + 1);
        }

        item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
    }
}
