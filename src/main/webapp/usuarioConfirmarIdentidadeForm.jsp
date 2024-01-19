<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Confirmar Identidade</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Confirmar Identidade</h1>

		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>

		<c:if test="${usuarioOuSenhaInvalido}">
			<fg:alertaUsuarioOuSenhaInvalido />
		</c:if>
		
		<div id="formVerticalContainer">
			<form action="autenticarUsuario" method="post">
				<div class="campo">
					<label for="login">Login</label>
					<input type="text"	name="login" value="${param.login}" />
					<p class="errorMessage" id="errorMsgLogin">${errorMsgLogin}</p>
				</div>
				<div class="campo">
					<label for="senhaAtual">Senha Atual</label> 
					<input type="password" name="senhaAtual" />
					<p class="errorMessage" id="errorMsgSenhaAtual">${errorMsgSenhaAtual}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Confirmar" name="btForm"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>