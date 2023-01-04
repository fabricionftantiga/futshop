window.onload = () => mostrar();

function mostrar(){
    for(var i = 0; i<= 600; i++) $('#linha-carrinho').remove();
    var valor = 0;
    $('#total-carrinho').remove();
    $.ajax({
        method: "GET",
        url: "mostrar",
        success: function (dados){
            dados.forEach(item => {
                listaItens(item);
                valor += item.precoFinal;
            });
            $('#texto-carrinho').append('<p class="total-carrinho" id="total-carrinho">R$ '+valor.toFixed(2)+'</p>');
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaItens(id){
    $.ajax({
        method: "POST",
        url: "adcionarCarrinho",
        data: "codigo="+id,
        success: function (dados){
            console.log("Sucesso")
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function alterar(codigo, acao){
    $.ajax({
        method: "PUT",
        url: "alterar",
        data: "codigo="+codigo+"&acao="+acao,
        success: function (dados){
            mostrar();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function excluir(){
    $.ajax({
        method: "DELETE",
        url: "excluirCarrinho",
        success: function (dados){
            mostrar();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function listaItens(dados){
    $('#itensCarrinho').append(
        '<tr class="linha-carrinho" id="linha-carrinho">'+
            '<td class="coluna-1">'+
                '<div class="img-carrinho"><img src="img/camisa-liverpool.png" class="imgs-carrinho"></div>'+
                '<div class="desc-carrinho">'+dados.descricaoProduto+' - '+dados.tamanho+'</div>'+
            '</td>'+
            '<td class="coluna-2">'+
                '<div class="texto-quantidade-carrinho"><p>'+dados.quantidade+'</p></div>'+
                '<div class="btns-quantidade-carrinho">'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+1+')">+</button>'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+2+')">-</button>'+
                '</div>'+
            '</td>'+
            '<td class="coluna-3">R$ '+dados.precoUnitario.toFixed(2)+'</td>'+
            '<td class="coluna-4">R$ '+dados.precoFinal.toFixed(2)+'</td>'+
        '</tr>'
    );
}