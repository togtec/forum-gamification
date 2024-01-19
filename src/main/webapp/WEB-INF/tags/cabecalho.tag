<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- define a imagem e o t�tulo do bot�o login para usu�rio deslogado -->
	<c:set var="btLogin" value="imagens/btDeslogado.png"/>
	<c:set var="titleBtLogin" value="Login"/>
<!-- define a imagem para o bot�o home para usu�rio deslogado -->
	<c:set var="btHome" value="imagens/btHomeDeslogado.png"/>

<div id="cabecalho">
	<!-- exibe o logotipo -->
	<a href="forumList"> <img src="imagens/logotipo.png" title="F�rum Gamification - Tudo sobre games" /> </a>
	
	<div id="usuario">				
		<c:if test="${usuarioLogado}" >
			<!-- define a imagem e o t�tulo do bot�o login para usu�rio logado -->
				<c:set var="btLogin" value="imagens/btLogado.png"/>
				<c:set var="titleBtLogin" value="Logout"/>
			<!-- define a imagem para o bot�o home para usu�rio logado -->
				<c:set var="btHome" value="imagens/btHomeLogado.png"/>
		
			<!-- exibe o diplay (nome do usu�rio e n�vel de acesso) -->
			<a href="exibirUsuarioConta" title="Conta" id="display"> ${nomeUsuario} - N�vel ${nivelUsuario} </a>
		</c:if>
		
		<!-- exibe bot�o home e bot�o login -->		
		<a href="forumList"> <img src="${btHome}" title="Home" name="btHome"></a>
		<a href="controleDeAcesso"> <img src="${btLogin}" title="${titleBtLogin}" name="btLogin"/></a>
	</div>
</div>