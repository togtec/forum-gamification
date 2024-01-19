<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>${param.forumNome}</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">${param.forumNome}</h1>
		
		<div id="containerConteudo">
			<div id="exibirLista">								
				<div id="barranovo" class="campo">
					<div>									 
						<a href="novoTopico?forumId=${param.forumId}&forumNome=${param.forumNome}" class="linkNovo">
							<img src="imagens/btNovo.png" name="btNovo" id="btNovo">
							Novo tópico
						</a>
					</div>
					<div><a href="forumList" class="linkNovo" style="padding-right:19px;">Voltar</a></div>
				</div>
				
				<!-- exibe lista de tópicos pertencentes ao fórum escolhido -->
				<c:forEach var="topico" items="${topicoList}" >
					<div id="primeiraLinha" class="campo">
						<div>
							<h2><a class="linkTopico" href="comentarioListByTopico?topicoId=${topico.getTopicoId()}">${topico.getTopicoNome()}</a></h2>
							<p class="alturaLinhaSimples letraCinza">${topico.getUsuarioNome()} (${topico.getUsuarioLogin()})</p>
						</div>			
						<div id="displayData">
							<!-- se tópico foi criado por usuário logado, exibe botão editar -->
							<c:if test="${topico.getUsuarioLogin() eq loginUsuario}">
								<a href="editarTopico?forumId=${param.forumId}&topicoId=${topico.getTopicoId()}" title="Editar"><img src="imagens/btEditar.png" name="btEditar"></a>
							</c:if>
							${topico.getData()}
						</div>
					</div>
					
					<div id="ultimaLinha" class="campo"><p>${topico.getTopicoConteudo()}</p></div>											
				</c:forEach>
			</div>
		</div>		
		
	</div>
</body>
</html>