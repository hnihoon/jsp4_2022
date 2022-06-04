<%@page import="koreait.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member List</title>
</head>
<body>
<!-- request 객체의 애트리뷰트 가져오기. -->
${list}<br>
<c:forEach var="vo" items="${list}">  <!-- java의 for each 와 동일하다.  -->
<br>이름 : ${vo.name } 
<br>이메일 :${vo.email }
<br>나이 :${vo.age }
<br>	
</c:forEach>
<%-- ${temp.name }<br>  --%> <!-- el을 사용할 때는 null에대한 get프로퍼티에 오류는 안생긴다. -->

</body>
</html>