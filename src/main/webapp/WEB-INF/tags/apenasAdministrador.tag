<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- se usuário não está logado -->
<c:if test="${not usuarioLogado}">
	<c:redirect url="controleDeAcesso"/>
</c:if>

<!-- se usuário não é Administrador -->
<c:if test="${nivelUsuario ne 'Administrador'}">
	<c:redirect url="administradorOnly"/>
</c:if>