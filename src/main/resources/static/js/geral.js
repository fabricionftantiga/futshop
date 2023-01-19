function verificarEstadoDeLogin(){
    if(localStorage.getItem('logado') == 'logado'){
        document.getElementById('login').style.display="none";
        document.getElementById('sair').style.display="initial";
    }
    else{
        document.getElementById('sair').style.display="none";
        document.getElementById('login').style.display="initial";
    }
}


function verificarLogin(){
    if(localStorage.getItem('logado') == 'logado'){
        $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
        listarItens()
    }
    else $("#estadoUSuario").html("Faça login");
}


function renderizarQuantidade(quantidade){
    if(localStorage.getItem('logado') == 'logado'){
        localStorage.setItem('quantidadeItens', quantidade);
        $('#quantidade-carrinho').html(localStorage.getItem('quantidadeItens'));
    }
    else $('#quantidade-carrinho').html("0");
}


function abrirMeusDados(){
    if(localStorage.getItem('logado') == 'logado') location.href="meusDados.html";
    else{
        document.getElementById('containerLogin').classList.add('ativo');
        alert("Faça login para poder vizualizar seus dados");
    }
}