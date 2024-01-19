<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>${topico.getTopicoNome()}</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">${topico.getTopicoNome()}</h1>
		
		<div id="containerConteudo">
			<div id="exibirLista">
				<div id="barranovo" class="campo" style="margin-bottom:20px;">
					<div></div>
					<div><a href="topicoListByForum?forumId=${topico.getForumId()}&forumNome=${topico.getForumNome()}" class="linkNovo" style="padding-right:19px;">Voltar</a></div>
				</div>
		
				<div style="border-bottom: 2px solid #cccccc; margin-bottom: 15px; padding-bottom:10px; padding-top: 10px; background-color: #FCFCFC; border-radius: 25px 0px 0px 0px; /* top-left corner, top-right, bottom-right, bottom-left */"> 
					<div style="display: flex; align-items:flex-start; justify-content: space-between; padding: 0px 10px 0px 10px;">
						<div>
							<p class="alturaLinhaSimples">${topico.getTopicoNome()}</p>
							<p class="alturaLinhaSimples">${topico.getUsuarioNome()} (${topico.getUsuarioLogin()})</p>
							<p class="alturaLinhaSimples letraCinza">${topico.getForumNome()}</p>
						</div>
						<div>${topico.getData()}</div>
					</div>
					<div style="padding: 20px 10px 0px 10px;"><p>${topico.getTopicoConteudo()}</p></div>
				</div>			
				<!-- exibe lista de comentários -->				
				<c:forEach var="comentario" items="${comentarioList}" >
					<div id="primeiraLinha" class="campo">
						<div><p class="alturaLinhaSimples negrito">${comentario.getUsuarioNome()} (${comentario.getUsuarioLogin()})</p></div>							
						<div id="displayData">
							<!-- se comentário foi criado por usuário logado, exibe botão editar -->
							<c:if test="${comentario.getUsuarioLogin() eq loginUsuario}">
								<a href="editarComentario?comentarioId=${comentario.getComentarioId()}" title="Editar"><img src="imagens/btEditar.png" name="btEditar"></a>
							</c:if>							
							${comentario.getDataHora()}
						</div>
					</div>
					<div id="ultimaLinha" class="campo"><p>${comentario.getComentario()}</p></div>
				</c:forEach>
			</div>
		</div>		
		
		<form action="inserirComentario" method="post" class="centralizado">
			<div class="campo">
				<input type="hidden" name="topicoId" value="${topico.getTopicoId()}" />
			</div>	
			<div class="campo"> 
				<textarea rows="6" name="comentario" style="width: 720px;"></textarea>
				<p class="errorMessage" id="errorMsgComentario">${errorMsgComentario}</p>
			</div>
			<div class="campo" style="margin-top: 20px; margin-bottom: 50px;">
				<input class="botao" type="submit" value="Adicionar Comentário" name="btForm"/>
			</div>
		</form>
		
	</div>
</body>
</html>