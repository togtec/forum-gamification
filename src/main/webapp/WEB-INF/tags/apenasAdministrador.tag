<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- se usu�rio n�o est� logado -->
<c:if test="${not usuarioLogado}">
	<c:redirect url="controleDeAcesso"/>
</c:if>

<!-- se usu�rio n�o � Administrador -->
<c:if test="${nivelUsuario ne 'Administrador'}">
	<c:redirect url="administradorOnly"/>
</c:if>