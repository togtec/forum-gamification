<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>
<fg:apenasUsuarioLogado />

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Conta Fórum Gamification</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Conta Fórum Gamification</h1>

		<div id="containerConteudo">
			<table class="tabelaLista">
				<tr>
					<td>Login:</td>
					<td><p id="contaLogin">${usuario.getLogin()}</p></td>
					<td></td>
				</tr>
				<tr>
					<td>E-mail:</td>
					<td><p id="contaEmail">${usuario.getEmail()}</p></td>
					<td><a class="linkAzul" href="usuarioEditarEmailForm.jsp?email=${usuario.getEmail()}">editar e-mail</a></td>
				</tr>
				<tr>
					<td>Nome:</td>
					<td><p id="contaNome">${usuario.getNome()}</p></td>
					<td><a class="linkAzul" href="usuarioEditarNomeForm.jsp?nome=${usuario.getNome()}">editar nome</a></td>
				</tr>
				<tr>
					<td>Status:</td>
					<td><p id="contaStatus">${usuario.getStatus().getDescStatus()}</p></td>
					<td></td>
				</tr>
				<tr>
					<td>Nível:</td>
					<td><p id="contaNivel">${usuario.getNivel().getDescNivel()}</p></td>
					<td></td>
				</tr>
				<tr>
					<td>Pontos:</td>
					<td><p id="contaPontos">${usuario.getPontos()}</p></td>
					<td><a class="linkAzul" href="exibirUsuarioRanking">exibir ranking</a></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>