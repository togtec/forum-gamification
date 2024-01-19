<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fg"%>

<html lang="pt-BR">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/estilos.css">
		<title>Login</title>
	</head>
	<body>
		<div id="containerPaginaLogin">		
			<div id="containerBoxLogin">
				<c:if test="${erroUsuarioDeslogado}">					
					<div style="background-color: #efcbd9; border: 1px solid #cccccc; padding: 10px; border-radius: 5px; margin: 20px;">
						<img src="imagens/erro.png" style="float: left; margin-right: 10px;">
							<p id="errorMsg" style="font-weight: bold;">Você precisa estar logado para acessar este recurso!</p>
							<p>Faça login abaixo ou crie uma nova conta se precisar.</p>
					</div>
				</c:if>
			
				<div id="esquerda">
					<a href="forumList">
						<img src="imagens/logotipo_mini.png" title="Fórum Gamification - Tudo sobre games">
					</a>
					<p id="participe">Participe do fórum com toda a segurança que você precisa.</p>
					<div>
						<p>Ainda não tem uma conta?<p>
						<a href="usuarioCadastrarForm.jsp">CRIE SUA CONTA AGORA</a>
					</div>
				</div>

				<div id="direita">
					<form action="login" method="post">
						<p id="titulo">Login<p>						
						
						<c:if test="${usuarioOuSenhaInvalido}">
							<div id="usuarioOuSenhaInvalidoContainer">
								<div>
									<img src="imagens/erro.png">
									<p id="errorMsg">Login ou senha incorretos!</p>
								</div>
								<p id="texto">Cuidado com as teclas Caps Lock e Shift, pois diferenciam letras maiúsculas e minúsculas.</p>
							</div>
						</c:if>						
						
						<input type="text" name="login" id="login" placeholder="Login" required >
						<input type="password" name="senha" id="senha" placeholder="Senha" required >
						<input type="submit" value="Entrar" name="btForm">
						<a class="linkAzulClaro" style="font-size: 14px;" href="usuarioConfirmarIdentidadeForm.jsp" >Redefinir senha</a>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>