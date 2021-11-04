function doPostUsuario(url, idU, nome, senha, espacoSolicitado, espacoDisponivel) {
    fetch(url, {
        method: 'POST',
        body:JSON.stringify({
            id: idU,
            nome: nome,
            senha: senha,
            espacoSolicitado: espacoSolicitado,
            espacoDisponivel: espacoDisponivel
        }), 
        headers:{
            "Content-Type":"application/json; charset=UTF-8"
        }
    })
    .then(function(response){
        return response.json()
    })
    .then(function(data){
        alert("Usuario cadastrado com sucesso, realize o login")
    })
}

var postUsuario = function (idU, nome, senha, espacoSolicitado, espacoDisponivel) {
    
    doPostUsuario('http://localhost:8080/api/usuarios/', idU, nome, senha, espacoSolicitado, espacoDisponivel);

}

function cadastrar() {
    var nome = document.getElementById("nome").value;
    var senha = document.getElementById("senha").value;
    var espacoSolicitado = document.getElementById("espacoSolicitado").value;
    postUsuario(100000000, nome, senha, espacoSolicitado, 0);
    
}