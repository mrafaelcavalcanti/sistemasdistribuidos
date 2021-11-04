<%@page import="com.ufape.sistemasdistribuidosserver.model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
lang="en">
<head>
<link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet"/>

    <link th:href="@{/webjars/bootstrap/css/bootstrap-theme.min.css}" rel="stylesheet"/>

    <link th:href="@{/webjars/font-awesome/css/font-awesome.min.css}" rel="stylesheet"/>

    <link rel="stylesheet" href="resources/css/loginCSS.css">
    <link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://code.iconify.design/1/1.0.7/iconify.min.js"></script>
    <script src="resources/js/login.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    

    <link th:href="@{/css/style.css}" rel="stylesheet"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
    <h1>TESTE</h1>

    <div id="cadastrar" class="cadastrar" >
        <label class="title">cadastrar</label><br>
        
        <input type="text" id="nome" class="campo" placeholder="NOME"><br>
        <input type="password" id="senha" class="campo" placeholder="SENHA"><br>
        <input type="text" id="espacoSolicitado" class="campo" placeholder="ESPACO SOLICITADO"><br>
        
        <button class="cadastrar-btn" onclick=cadastrar()>CADASTRAR</button><br><br>
    </div>

    <div class="form-entrar">
        <label class="title">login</label><br>
        <div th:if="${param.error}"></div>

        <form class="user" action="/login" method="post">
            <div class="form-group">
                <input id="entrar-usuario-username" name="username" value=""/>
            </div>

            <div class="form-group">
                <input id="entrar-usuario-password" type="password" name="password" value=""/>
            </div>

            <div class="form-group">
                <button id="entrar-usuario-entrar">ENTRAR</button>
            </div>

            <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
        </form>
    </div>
</body>
</html>