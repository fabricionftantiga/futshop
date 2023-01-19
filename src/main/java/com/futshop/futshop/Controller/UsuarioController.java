package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.AlreadyBoundException;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioModel> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping(path = "/{codigo}")
    public UsuarioModel buscarUsuarioPorID(@PathVariable Long codigo){
        return usuarioService.buscarUsuarioPorID(codigo);
    }

    @GetMapping(path = "/produtos/{codigo}")
    public List<CarrinhoModel> listarProdutosDeUmUsuario(@PathVariable Long codigo){
        return usuarioService.listarItensUsuario(codigo);
    }

    @PostMapping
    public ResponseEntity salvarUsuario(@RequestBody UsuarioModel usuario) throws AlreadyBoundException {
        return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping(path = "/email/{email}/senha/{senha}")
    public UsuarioModel fazerLogin(@PathVariable String email,
                                   @PathVariable String senha){
        return usuarioService.fazerLogin(email, senha);
    }

    @PutMapping(path = "/{codigo}")
    public UsuarioModel alterarEndereco(@PathVariable Long codigo,
                                        @RequestBody UsuarioModel endereco){
        return usuarioService.alterarEndereco(codigo, endereco);
    }

    @DeleteMapping
    public void excluirTodosUsuarios(){
        usuarioService.excluirTodosUsuarios();
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public void excluirUsuario(@PathVariable Long codigo){
       usuarioService.excluirUsuarioPorID(codigo);
    }
}