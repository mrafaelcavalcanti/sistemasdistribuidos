<%@page import="com.ufape.sistemasdistribuidosserver.model.Usuario" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="resources/js/home.js"></script>
    <title>Login</title>
</head>

<body>
    <div id="id"><sec:authentication property="principal.id" /></div>
    <div><sec:authentication property="principal.username" /></div>
    <div id="usuario-logado"><%=request.getRemoteUser() %></div>

    <form action="/logout" method="post" class="navbar-form navbar-right">
        <button type="submit" class="btn btn-default">
            <span class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></span>
            Sair
        </button>

        <input type="hidden" th:name="${_csrf.parameterName}"
            th:value="${_csrf.token}" />
    </form>

    <!-- lista arquivos e adiconar opção de enviar arquivo -->

    <div>
        <br><br>
        <h2>Upload de Arquivo</h2>
        <input type="file" id="arquivo">
        <button onclick="upload()">upload</button>
    </div>

    <div>
        <br><br>
        <h2>meus arquivos</h2>
        <div id="meus-arquivos">
            <thead>
                <tr>
                    <th> Nome |</th>
                    <th> Solicitar</th>
                </tr>
            </thead>
            <tbody id="arquivos-tabela"></tbody>
        </div>
    </div>
</body>

</html>