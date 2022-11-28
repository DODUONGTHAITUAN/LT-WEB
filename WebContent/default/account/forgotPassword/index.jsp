<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String context = request.getContextPath();
String url = context + "/default/account/login";
%>

<!doctype html>
<html lang="en">
<head>
<jsp:include page="../../head.jsp" />
<link rel="stylesheet" href="<%=url%>/index.css" />
<title>Đăng nhập tài khoản - Shoplane</title>
</head>
<body>
	<div id="app">
		<jsp:include page="../../header/header.jsp" />
		<main class="main">
			<div class="container">
				<section id="login">
					<div class="border border-1 rounded login-form">
						<form action="login" method="POST">
							<h1 class="login-header">KHÔI PHỤC MẬT KHẨU</h1>
							<input type="text" hidden name="caller" value="${caller}" /> <input
								class="form-control" type="email" name="email"
								placeholder="Email" required>
							<div class="err__msg">
								<span>${errMsg}</span>
							</div>
							<button class="btn-submit" type="submit">Gửi</button>
							<a class="register-text"
								style="font-size: 1.3rem; margin: 1rem 0;"
								href="<%=context%>/login"> Quay lại</a>
						</form>
					</div>
				</section>
			</div>
		</main>
		<jsp:include page="../../footer/footer.jsp" />
	</div>
	<jsp:include page="../../script.jsp" />
</body>
</html>