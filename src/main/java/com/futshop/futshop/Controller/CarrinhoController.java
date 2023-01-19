package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping(path = "/produto/{codigo}/usuario/{codigoCli}")
    public UsuarioModel adcionarItem(@PathVariable Long codigo,
                             @PathVariable Long codigoCli){
        return carrinhoService.adcionarItem(codigo, codigoCli);
    }

    @PutMapping(path = "/produto/{codigo}/usuario/{codigoCli}/acao/{acao}")
    public UsuarioModel alterarItem(@PathVariable Long codigo,
                                           @PathVariable Long codigoCli,
                                           @PathVariable Integer acao){
        return  carrinhoService.alterarQuantidadeItem(codigo, codigoCli, acao);
    }

    @DeleteMapping(path = "/produto/{codigoProd}/usuario/{codigoCli}")
    public UsuarioModel excluirItem(@PathVariable Long codigoProd,
                                    @PathVariable Long codigoCli){
        return carrinhoService.excluirItem(codigoProd, codigoCli);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public UsuarioModel excluirItens(@PathVariable Long codigo){
        return carrinhoService.excluirItens(codigo);
    }
}
