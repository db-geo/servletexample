<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${message != null}">
<div class="alert alert-info" role="alert">
    ${message}
</div>
</c:if>
<c:if test="${errorMessage != null}">
<div class="alert alert-danger" role="alert">
    ${errorMessage}
</div>
</c:if>