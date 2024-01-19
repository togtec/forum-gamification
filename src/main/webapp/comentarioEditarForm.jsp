<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg" %>
<fg:apenasUsuarioLogado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Editar Comentário</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Editar Comentário</h1>
		
		<c:if test="${erroDePreenchimento}">
			<fg:alertaErroDePreenchimento />
		</c:if>

		<div id="formVerticalContainer">
			<form action="atualizarComentario" method="post">
				<div class="campo">
					<input type="hidden" name="topicoId" value="${comentario.getTopicoId()}${param.topicoId}" />
				</div>			
				<div class="campo">
					<input type="hidden" name="comentarioId" value="${param.comentarioId}" />
				</div>			
			
				<div class="campo">
					<label for="forumNome">Fórum</label> 
					<input type="text" name="forumNome" value="${comentario.getForumNome()}${param.forumNome}" class="readonly" readonly/>
				</div>						
				<div class="campo">
					<label for="topicoNome">Tópico</label> 
					<input type="text" name="topicoNome" value="${comentario.getTopicoNome()}${param.topicoNome}" class="readonly" readonly/>
				</div>				
				<div class="campo">
					<label for="comentario">Comentário</label> 
					<textarea rows="10" name="comentario">${comentario.getComentario()}${param.comentario}</textarea>
					<p class="errorMessage" id="errorMsgComentario">${errorMsgComentario}</p>
				</div>
				<div class="campo">
					<input class="botao" type="submit" value="Salvar" name="btForm"/>
				</div>
			</form>
		</div>
		
	</div>
</body>
</html>