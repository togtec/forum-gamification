<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Usuário cadastrado com sucesso</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Usuário Cadastrado com Sucesso</h1>
		
		<div id="containerConteudo">
			<h2>${param.nome.split(" ")[0]}, <br/>
			agora você tem uma conta Fórum Gamification!</h2>

			<br/><br/>
		
			<div class="exibirBorda">
				<p class="negrito letraAzulClaro">Dados Registrados</p>
				<p class="recuoSimples">Login: ${param.login}</p>
				<p class="recuoSimples">E-mail: ${param.email}</p>
				<p class="recuoSimples">Nome: ${param.nome}</p>
			</div>
			
			<br/><br/><br/>
			
			<div class="centralizado">
				<a href="controleDeAcesso">
					<input type="button" class="botao" value="Continuar" />
				</a>
			</div>
		</div>
	</div>
</body>
</html>