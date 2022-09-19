<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<head>
<style>
.main {
	padding-top: 6rem;
}
</style>
</head>

<%
request.setCharacterEncoding("utf-8");
%>

<tags:base title="Giỏ hàng của bạn - SHOPLANE"
	css="../../assets/css/index.css">
	<jsp:include page="../header.jsp" />
	<main class="main">
		<h1>Cart</h1>
	</main>
	<jsp:include page="../footer.jsp" />
	<jsp:include page="../back-to-top.jsp" />
</tags:base>
