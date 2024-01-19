<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasUsuarioLogado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Cadastrar Tópico</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Cadastrar Tópico</h1>
		
		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>

		<div id="formVerticalContainer">
			<form action="inserirTopico" method="post">
				<div class="campo">
					<input type="hidden" name="forumId" value="${param.forumId}" />
				</div>
				
				<div class="campo">
					<label for="forumNome">Fórum</label> 
					<input type="text" name="forumNome" value="${param.forumNome}" class="readonly" readonly/>
					<p class="errorMessage" id="errorMsgForumNome">${errorMsgForumNome}</p>
				</div>								
				<div class="campo">
					<label for="topicoNome">Nome do Tópico</label> 
					<input type="text" name="topicoNome" value="${param.topicoNome}" />
					<p class="errorMessage" id="errorMsgTopicoNome">${errorMsgTopicoNome}</p>
				</div>				
				<div class="campo">
					<label for="topicoConteudo">Conteúdo</label> 
					<textarea rows="10" name="topicoConteudo">${param.topicoConteudo}</textarea>
					<p class="errorMessage" id="errorMsgTopicoConteudo">${errorMsgTopicoConteudo}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Cadastrar" name="btForm"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>