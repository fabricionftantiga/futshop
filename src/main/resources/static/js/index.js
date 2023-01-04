window.onload = () => lista();

function lista(){
    var i = 0;
    //Promoções
    $.ajax({
        method: "GET",
        url: "buscarPromocoes",
        success: function (dados){
            dados.forEach(item => {
                i++;
                if(item.quantidadeEstoque > 0 && i <= 4) adcionaProduto(item, "section-promocao");
                if(item.quantidadeEstoque > 0 && i <= 4) adcionaProduto(item, "section-novidades");
            });
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });

    /*Novidades
     $.ajax({
         method: "GET",
         url: "buscarPromocoes",
         success: function (dados){
             dados.forEach(item => {
                 i++;
                 if(item.quantidadeEstoque > 0 && i <= 4) adcionaProduto(item, "section-novidades");
             });
         }
     }).fail(function(xhr, status, errorThrown){
         alert("Erro ao salvar: " +xhr.responseText);
     });*/

    //Produtos
    $.ajax({
        method: "GET",
        url: "listarProdutos",
        success: function (dados){
            dados.forEach(item => adcionaProduto(item, "section-produto"));
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaProduto(dados, local){
    $('#'+local).append(
        '<div class="produto">'+
            '<a class="link" href="detalhe.html" onclick="pegarId('+dados.codigo+')">'+
                '<header class="promo-produto">'+
                    '<p class="numero-promo">'+dados.promocao+'%</p><p class="texto-promo">OFF</p>'+
                '</header>'+
                '<div class="img-produto">'+
                    '<img src="img/camisa-liverpool.png" width="100%">'+
                '</div>'+
                '<div class="desc-produto" >'+dados.descricao+" - "+dados.tamanho+'</div>'+
                '<div class="valor-produto" >R$ '+dados.valor+'</div>'+
            '</a>'+

            '<button class="adciona-carrinho" onclick="adcionaItens('+dados.codigo+')">Adcionar ao carrinho</button>'+
        '</div>'
    );
}