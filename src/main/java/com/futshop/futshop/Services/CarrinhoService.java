package com.futshop.futshop.Services;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public UsuarioModel adcionarItem( Long codigo, Long codigoCli){
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
                itens.setImagem(produto.getImagem());
                itens.setDescricaoProduto(produto.getDescricao());
                itens.setTamanho(produto.getTamanho());
                itens.setQuantidade(1);
                itens.setPrecoUnitario(produto.getValorComDesconto());
                itens.setPrecoFinal(itens.getPrecoUnitario());

                usuario.setItens(itens);
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
            }
        }

        calc(usuario);
        produtoRepository.save(produto);
        return usuarioRepository.save(usuario);
    }

    public void calc(UsuarioModel usuario){
        Integer quantiadeTotal = 0;
        Double valorTotal = 0.0;

        for(CarrinhoModel itens: usuario.getItens()){
            quantiadeTotal += itens.getQuantidade();
            valorTotal += itens.getPrecoFinal();
        }

        usuario.setQuantidadeItens(quantiadeTotal);
        usuario.setValorTotalItens(valorTotal);
        usuarioRepository.save(usuario);
    }

    public UsuarioModel alterarQuantidadeItem(Long codigoProd, Long codigoUSer, Integer acao){
        ProdutoModel produto = produtoRepository.buscarPorID(codigoProd);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoUSer);

        for(CarrinhoModel item: usuario.getItens()){
            if(item.getCodigo() == codigoProd && acao == 1 && produto.getQuantidadeEstoque() > 0) {
                soma(produto, item);
                break;
            }
            if(item.getCodigo() == codigoProd && acao == 2){
                subtracao(produto, item, codigoUSer);
                break;
            }
        }

        calc(usuario);
        produtoRepository.save(produto);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel excluirItem( Long codigoProd, Long codigoCli){
        ProdutoModel produto = produtoRepository.buscarPorID(codigoProd);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        for(int i = 0; i <= usuario.getItens().size(); i++){
            if(usuario.getItens().get(i).getCodigo() == codigoProd){
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + usuario.getItens().get(i).getQuantidade());
                usuario.getItens().remove(i);
                break;
            }
        }

        calc(usuario);
        produtoRepository.save(produto);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel excluirItens( Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel produto: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(produto.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + produto.getQuantidade());
            produtoRepository.save(prod);
        }

        usuario.getItens().clear();
        calc(usuario);
        return  usuarioRepository.save(usuario);
    }

    public void soma(ProdutoModel produto, CarrinhoModel item){
        item.setQuantidade(item.getQuantidade() + 1);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
        item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
    }

    public void subtracao(ProdutoModel produto, CarrinhoModel item, Long codigoCli){
        if(item.getQuantidade() == 1) excluirItem(produto.getCodigo(), codigoCli);
        else{
            item.setQuantidade(item.getQuantidade() - 1);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }
    }
}
