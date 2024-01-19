<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Cadastrar Usuário</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Cadastrar Usuário</h1>

		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>
		
		<div id="formVerticalContainer">
			<form action="inserirUsuario" method="post">
				<div class="campo">
					<label for="login">Login</label>
					<input type="text"	name="login" value="${param.login}" />
					<p class="errorMessage" id="errorMsgLogin">${errorMsgLogin}</p>
				</div>
				<div class="campo">
					<label for="email">E-mail</label> 
					<input type="email" name="email" value="${param.email}" />
					<p class="errorMessage" id="errorMsgEmail">${errorMsgEmail}</p>
				</div>
				<div class="campo">
					<label for="nome">Nome</label> 
					<input type="text" name="nome" value="${param.nome}" />
					<p class="errorMessage" id="errorMsgNome">${errorMsgNome}</p>
				</div>
				<div class="campo">
					<label for="senha">Senha</label> 
					<input type="password" name="senha" />
					<p class="errorMessage" id="errorMsgSenha">${errorMsgSenha}</p>
				</div>
				<div class="campo">
					<label for="confirmarSenha">Confirmar Senha</label>
					<input type="password" name="confirmarSenha" />
					<p class="errorMessage" id="errorMsgConfirmarSenha">${errorMsgConfirmarSenha}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Cadastrar" name="btForm"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>