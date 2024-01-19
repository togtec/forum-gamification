<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasUsuarioLogado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Ranking</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Ranking</h1>
		
		<table class="tabelaLista">
			<c:set var="contador" value="1" />
			<c:forEach var="usuario" items="${ranking}">
				
				<!-- define valor da variável pt (ponto ou pontos) -->
				<c:choose>
					<c:when test="${usuario.getPontos() == 1}">
						<c:set var="pt" value="ponto" />
					</c:when>
					<c:otherwise>
						<c:set var="pt" value="pontos" />
					</c:otherwise>
				</c:choose>
				
				<!-- define a cor da letra (vermelha para imprimir o usuário logado; preta para imprimir os demais) -->
				<c:choose>
					<c:when test="${usuario.getLogin() eq loginUsuario}">
						<c:set var="corDaLetra" value="letraVermelha" />
					</c:when>
					<c:otherwise>
						<c:set var="corDaLetra" value="letraPreta" />
					</c:otherwise>
				</c:choose>
				
				<tr>
					<td style="text-align: right;">
						<p class="${corDaLetra}"> ${contador} </p>
					</td>
					<td>
						<p class="${corDaLetra}"> ${usuario.getNome()} </p>
					</td>
					<td style="text-align: center;">
						<p class="${corDaLetra}"> ${usuario.getLogin()} </p>
					</td>
					<td style="text-align: right; padding: 0px 0px 0px 20px;">
						<p class="${corDaLetra}"> ${usuario.getPontos()} </p>
					</td>
					<td style="text-align: left; padding: 0px;">
						<p class="${corDaLetra}"> ${pt} </p>
					</td>
				</tr>
				<c:set var="contador" value="${contador + 1}" />
				
			</c:forEach>
		</table>
	</div>
</body>
</html>