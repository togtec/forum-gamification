<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not usuarioAutenticado}">
	<c:redirect url="controleDeAcesso"/>
</c:if>