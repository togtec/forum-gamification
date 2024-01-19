<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasAdministrador />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Editar Fórum</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Editar Fórum</h1>
		
		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>
		
		<div id="formVerticalContainer">
			<form action="atualizarForum" method="post">
				<div class="campo">
					<input type="hidden" name="forumId" value="${param.forumId}" />
				</div>				
				<div class="campo">
					<label for="forumNome">Nome do Fórum</label> 
					<input type="text" name="forumNome" value="${forum.getForumNome()}${param.forumNome}" />
					<p class="errorMessage" id="errorMsgForumNome">${errorMsgForumNome}</p>
				</div>				
				<div class="campo">
					<label for="forumDescricao">Descrição</label> 
					<textarea rows="10" name="forumDescricao">${forum.getForumDescricao()}${param.forumDescricao}</textarea>
					<p class="errorMessage" id="errorMsgForumDescricao">${errorMsgForumDescricao}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Salvar" name="btForm"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>