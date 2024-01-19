<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Erro</title>
</head>
<body>
	<div id="containerPaginaGeral">
		<fg:cabecalho />
		<h1 id="tituloPagina" class="centralizado">Erro</h1>
		
		<div id="containerConteudo">
			<p class="centralizado" id="errorMsg">${exception.getMessage()}</p>
		</div>
	</div>
</body>
</html>