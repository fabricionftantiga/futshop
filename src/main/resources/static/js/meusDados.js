window.onload = () => {
    verificarLogin();
}

function listarDados(){
    var oculta = "";
    $.ajax({
        method: "GET",
        url: "/usuario/"+localStorage.getItem('codigo'),
        success: function (dados){
            for(i = 0; i <= dados.senha.length; i++) oculta += "*"
            $("#nomeUsuarioExibir").html(dados.nome.split(" ")[0]);
            $("#nomeExibir").html(dados.nome);
            $("#dataExibir").html(dados.dataNascimento);
            $("#cpfExibir").html(dados.cpf);
            $("#emailExibir").html(dados.email);
            $("#senhaExibir").html(oculta);
            $("#celularExibir").html(dados.celular);
            $("#cepExibir").html(dados.cep);
            $("#estadoExibir").html(dados.estado);
            $("#cidadeExibir").html(dados.cidade);
            $("#bairroExibir").html(dados.bairro);
            $("#ruaExibir").html(dados.rua);
            $("#numeroExibir").html(dados.numero);
            $("#complementoExibir").html(dados.complemento);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao sallet: " +xhr.responseText);
    });
}