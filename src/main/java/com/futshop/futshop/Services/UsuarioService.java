package com.futshop.futshop.Services;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public List<CarrinhoModel> listarItensUsuario(Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);
        List<CarrinhoModel> lista = new ArrayList<CarrinhoModel>();

        for(CarrinhoModel produtos: usuario.getItens()){
            lista.add(produtos);
        }

        return lista;
    }

    public UsuarioModel buscarUsuarioPorID(Long codigo){
        return usuarioRepository.buscarPorID(codigo);
    }

    public ResponseEntity salvarUsuario(UsuarioModel usuario) throws AlreadyBoundException {

        for(UsuarioModel user: usuarioRepository.findAll()){
            if(user.getEmail().equals(usuario.getEmail())) throw new AlreadyBoundException();
        }

        usuarioRepository.save(usuario);
        return new ResponseEntity("Usuário cadastrado com sucesso", HttpStatus.OK);
    }

    public UsuarioModel fazerLogin(String email, String senha){
        return usuarioRepository.fazerlogin(email, senha);
    }

    public UsuarioModel alterarEndereco(Long codigo, UsuarioModel endereco){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        usuario.setCep(endereco.getCep());
        usuario.setEstado(endereco.getEstado());
        usuario.setCidade(endereco.getCidade());
        usuario.setBairro(endereco.getBairro());
        usuario.setRua(endereco.getRua());
        usuario.setNumero(endereco.getNumero());
        usuario.setComplemento(endereco.getComplemento());

        return usuarioRepository.save(usuario);
    }

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

    public void excluirUsuarioPorID(Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel item: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(prod);
        }

        usuarioRepository.deleteById(codigo);
    }
}
