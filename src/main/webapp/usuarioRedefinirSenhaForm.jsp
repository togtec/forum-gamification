<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasUsuarioAutenticado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Redefinir Senha</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Redefinir Senha</h1>

		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>
		
		<div id="formVerticalContainer">
			<form action="atualizarUsuarioSenha" method="post">
				<div class="campo">
					<label for="login">Login</label>
					<input type="text"	name="login" value="${param.login}" class="readonly" readonly/>
					<p class="errorMessage" id="errorMsgLogin">${errorMsgLogin}</p>
				</div>
				<div class="campo">
					<label for="senhaAtual">Senha Atual</label> 
					<input type="password" name="senhaAtual" value="${param.senhaAtual}" class="readonly" readonly />
					<p class="errorMessage" id="errorMsgSenhaAtual">${errorMsgSenhaAtual}</p>
				</div>				
				<div class="campo">
					<label for="novaSenha">Nova Senha</label> 
					<input type="password" name="novaSenha" />
					<p class="errorMessage" id="errorMsgNovaSenha">${errorMsgNovaSenha}</p>
				</div>
				<div class="campo">
					<label for="confirmarNovaSenha">Confirmar Nova Senha</label>
					<input type="password" name="confirmarNovaSenha" />
					<p class="errorMessage" id="errorMsgConfirmarNovaSenha">${errorMsgConfirmarNovaSenha}</p>
				</div>				
				<div class="campo">
					<input class="botao" type="submit" value="Redefinir" name="btForm"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>