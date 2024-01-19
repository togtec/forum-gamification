<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Home</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Seja Bem Vindo!</h1>
		
		<div id="containerConteudo">
			<div id="exibirLista">								
				<div id="barranovo" class="campo">
					<!-- se usuário está logado e tem nível Administrador, exibe botão "Novo fórum"  -->
					<c:if test="${nivelUsuario eq 'Administrador'}">
						<div><a href="forumCadastrarForm.jsp" class="linkNovo"> <img src="imagens/btNovo.png" name="btNovo" id="btNovo">Novo fórum</a></div>
					</c:if>
				</div>
				
				<!-- exibe lista de fóruns -->
				<c:forEach var="forum" items="${forumList}" >
					<div id="primeiraLinha" class="campo">
						<div>													
							<h2><a class="linkTopico" href="topicoListByForum?forumId=${forum.getId()}&forumNome=${forum.getForumNome()}">${forum.getForumNome()}</a></h2>
							<p class="alturaLinhaSimples letraCinza">${forum.getUsuarioNome()} (${forum.getUsuarioLogin()})</p>
						</div>							
						<div id="displayData">
							<!-- se fórum foi criado por usuário logado, exibe botão editar -->
							<c:if test="${forum.getUsuarioLogin() eq loginUsuario}">
								<a href="editarForum?forumId=${forum.getId()}" title="Editar"><img src="imagens/btEditar.png" name="btEditar"></a>
							</c:if>							
							${forum.getDataDeInicio()}
						</div>
					</div>
					<div id="ultimaLinha" class="campo"><p>${forum.getForumDescricao()}</p></div>
				</c:forEach>
			</div>
		</div>
		
	</div>
</body>
</html>