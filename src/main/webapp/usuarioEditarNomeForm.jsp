<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasUsuarioLogado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Editar nome</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Editar nome</h1>

		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>
		
		<div id="formVerticalContainer">
			<form action="atualizarUsuarioNome" method="post">
				<div class="campo">
					<label for="nome">Nome</label> 
					<input type="text" name="nome" value="${param.nome}" />
					<p class="errorMessage" id="errorMsgNome">${errorMsgNome}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Salvar" name="btForm"/>
				</div>				
			</form>
		</div>
	</div>
</body>
</html>