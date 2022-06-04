<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- sessionCheck.jsp -->
<c:if test="${member==null }">
	<script>
		alert('로그인을 해야합니다.!!');
		location.href='${pageContext.request.contextPath}/login';
	</script>
</c:if>
