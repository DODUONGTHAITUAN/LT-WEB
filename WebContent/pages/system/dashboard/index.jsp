<%@page import="com.shoplane.utils.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.shoplane.services.client.ProductService"%>

<%
request.setCharacterEncoding(Constants.UTF8);
response.setCharacterEncoding(Constants.UTF8);
String context = request.getContextPath();
%>

<!doctype html>
<html lang="en">
<head>
<jsp:include page="../head.jsp" />
<title>Overview - SHOPLANE</title>
<style>
.cards {
	display: flex;
	width: 100%;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 5rem;
}

.cards__item {
	border: 0.5px solid #5ec9ff;
	border-radius: 0.4rem;
	min-width: calc(25% - 5rem);
	padding: 1rem;
	background-color: #f4f6fa;
}

.cards__body {
	display: flex;
	align-items: center;
	gap: 2rem;
}

.cards__body>svg {
	font-size: 5rem;
	color: #00b5ad;
}

.cards__content {
	display: flex;
	align-items: center;
	flex-direction: column;
	gap: 2rem;
}

.cards__content>.cards__content-title {
	font-size: 1.4rem;
}

.cards__content>.cards__content-count {
	font-size: 2.5rem;
	font-weight: 700;
}

.cards__div {
	width: 100%;
	height: 0.5px;
	background-color: #d2d2d2;
	margin: 2rem 0;
}

.cards__footer {
	display: flex;
	align-items: center;
	gap: 1rem;
	font-size: 1.2rem;
}

.cards__footer>svg {
	color: #5ec9ff;
}

.cards__footer>span {
	color: #a9a9a9;
}

.charts {
	display: flex;
	align-items: center;
	gap: 3rem;
	justify-content: space-between;
	margin-bottom: 5rem;
}

.charts__item {
	min-width: calc(50% - 3rem);
}
</style>
</head>
<body>
	<div id="app">
		<div id="sidebar">
			<jsp:include page="../components/sidebar.jsp" />
		</div>
		<div id="content">
			<div id="header">
				<jsp:include page="../components/header.jsp" />
			</div>
			<main id="main-content">
				<div class="cards">
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">Đơn hàng mới</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer">
							<i class="fas fa-calendar"></i> <span>Trong vòng một tuần</span>
						</div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
				</div>
				<div class="charts">
					<div class="charts__item">
						<div class="canvas-wrapper">
							<canvas class="chart" id="trafficflow"></canvas>
						</div>
					</div>
					<div class="charts__item">
						<div class="canvas-wrapper">
							<canvas class="chart" id="sales"></canvas>
						</div>
					</div>
				</div>
				<div class="cards">
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">Đơn hàng mới</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer">
							<i class="fas fa-calendar"></i> <span>Trong vòng một tuần</span>
						</div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
					<div class="cards__item">
						<div class="cards__body">
							<i class="teal fas fa-shopping-cart"></i>
							<div class="cards__content">
								<span class="cards__content-title">NEW ORDERS</span> <span
									class="cards__content-count">6,267</span>
							</div>
						</div>
						<div class="cards__div"></div>
						<div class="cards__footer"></div>
					</div>
				</div>
				<div class="charts">
					<div class="charts__item">
						<div class="canvas-wrapper">
							<canvas id="myPieChart"></canvas>
						</div>
					</div>
					<div class="charts__item">
						<canvas id="myAreaChart"></canvas>
					</div>
				</div>
			</main>
		</div>
	</div>
	<!-- Jquery -->
	<jsp:include page="../components/script.jsp"></jsp:include>
	<script type="text/javascript"
		src="<%=context%>/assets/js/system/dashboard/chart.js"></script>
</body>
</html>

