function doPostArquivo(url, idA, idU, nome, conteudo) {
    fetch(url, {
        method: 'POST',
        body: JSON.stringify({
            id: idA,
            idUsuario: idU,
            nome: nome,
            conteudo: conteudo
        }),
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        }
    })
        .then(function (response) {
            return response.json()
        })
        .then(function (data) {
            alert("upload de arquivo com sucesso")
        })
}

var postArquivo = function (idA, idU, nome, conteudo) {
    doPostArquivo('http://localhost:8080/api/arquivos/novo', idA, idU, nome, conteudo);
}

async function upload() {
    var idU = Number(document.getElementById('id').textContent);
    var fullPath = document.getElementById('arquivo').value;
    var file = document.getElementById('arquivo').files[0];
    var conteudo = await toBase64(file); //await getBase64(file);
    if (fullPath) {
        var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        var nome = fullPath.substring(startIndex);
        if (nome.indexOf('\\') === 0 || nome.indexOf('/') === 0) {
            nome = nome.substring(1);
        }
    }
    nome = nome + "," + conteudo.split(',')[1];
    postArquivo(100000000, idU, nome, undefined);
}

function readFile(file) {
    return new Promise((resolve, reject) => {
        // Create file reader
        let reader = new FileReader();

        // Register event listeners
        reader.addEventListener("loadend", e => resolve(e.target.result));
        reader.addEventListener("error", reject);

        // Read file
        reader.readAsArrayBuffer(file);
    })
}

async function getAsByteArray(file) {
    return new Uint8Array(await readFile(file));
}


// upload2 usado apenas para teste
function upload2() {
    var idUsuario = document.getElementById('id').innerHTML;
    var fullPath = document.getElementById('arquivo').value;
    let arquivo = document.getElementById("arquivo").files[0];
    let formData = new FormData();
    if (fullPath) {
        var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        var nome = fullPath.substring(startIndex);
        if (nome.indexOf('\\') === 0 || nome.indexOf('/') === 0) {
            nome = nome.substring(1);
        }
    }

    formData.append("id", 1000000000);
    formData.append("idUsuario", idUsuario);
    formData.append("nome", nome);
    formData.append("conteudo", arquivo);
    fetch('http://localhost:8080/api/arquivos/novo', { method: "POST", body: formData });
}

function getBase64(file) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        return reader.result;
    };
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}


var toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});


window.onload = function () {
    var id = document.getElementById("id").textContent;
    carregarArquivos(id);
}

async function carregarArquivos(id) {
    var url = 'http://localhost:8080/api/arquivos/listar/' + id;
    console.log(url);
    fetch(url)
        .then(function (response) {
            if (!response.ok) throw new Error('Erro ao obter carrinho, status' + response.status);
            return response.json();
        }).then(function (data) {
            console.log(data);
            arquivos = data;
            var lista = "";
            lista = "<tbody id='arquivos-tabela'>";
            for (var i = 0; i < data.length; i++) {
                lista += "<tr>"
                lista += "<td>" + arquivos[i].nome + "</td>";
                lista += "<td> <button onclick='solicitar(" + arquivos[i].id + ")'>Solicitar arquivo</button> </td>";
                lista += "</tr> <br>";
            }
            lista += "</tbody>"
            document.getElementById('meus-arquivos').innerHTML = lista;
            //document.getElementById("nome-usuario-logado").innerHTML = '<a href="/usuario">' + data.nome + '</a>'
        })
        .catch(function (response) {
            console.error(error.message);
        });
}

function solicitar(id) {
    postSolicitar(id);
}

function doPostSolicitar(url, idA) {
    fetch(url, {
        method: 'POST',
        body: JSON.stringify({
            idArquivo: idA,
        }),
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        }})
        .then(function (response) {
            return response.json()
        })
        .then(function (data) {
            alert("upload solicitado com sucesso")
        })
}

var postSolicitar = function (idA) {
    doPostSolicitar('http://localhost:8080/api/requisicoes/solicitar', idA);
}