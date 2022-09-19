<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<head>
<style>
.catalog {
	margin: 8rem 0;
}

.catalog__heading {
	margin: 0 auto;
	text-transform: uppercase;
	margin-bottom: 3rem;
	position: relative;
	width: fit-content;
	text-transform: uppercase;
}

.catalog__heading::after {
	position: absolute;
	content: "";
	height: 2px;
	width: 100%;
	background-color: #000;
	bottom: -0.4rem;
	left: 0;
	border-radius: 0.4rem;
	background-color: #5ec9ff;
}

.catalog__list {
	display: flex;
	align-items: flex-start;
	justify-content: space-between;
	flex-wrap: wrap;
	gap: 2.5rem 2rem;
}

.group_content {
	box-shadow: rgba(0, 0, 0, 0.1) 0px 3px 8px;
	overflow: hidden;
	border-radius: 1rem;
	min-width: calc(( 1200px - 60px)/4);
	transition: all linear 0.2s;
}

.group_content:hover {
	box-shadow: rgba(0, 0, 0, 0.3) 0px 3px 8px;
}

.home-product-item__img {
	width: inherit;
	height: 25rem;
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

.home-product-item__name {
	font-size: 1.5rem;
	margin-top: 8px;
}

.shop_name {
	font-size: 1.6rem;
	margin: 4px 0;
}

.home-product-item__name, .shop_name, .home-product-item__rating,
	.home-product-item__price {
	padding: 0 8px;
}

.home-product-item__rating ion-icon {
	font-size: 1.3rem;
}

.home-product-item__price {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	padding-top: 8px;
	padding-bottom: 16px;
	border-top: 1px solid #eae9e9;
}

.home-product-item__price-current {
	font-size: 1.4rem;
	font-weight: 600;
	color: red;
}

.home-product-item__price-old {
	font-size: 1.4rem;
	font-weight: 600;
	text-decoration: line-through;
}

.btn-more {
margin: 0 auto;
}

.btn-more-link {
	display: flex;
	padding: 1rem 1.5rem;
	border: 1px solid #5ec9ff;
	border-radius: 0.4rem;
	color: #5ec9ff;
	font-weight: 500;
	transition: all linear 0.2s;
}

.btn-more-link:hover {
	background-color: #5ec9ff;
	color: #fff;
}
</style>
</head>

<section class="catalog">
	<div class="container">
		<h1 class="catalog__heading">${param.heading}</h1>
		<div class="catalog__list">
			<%
			for (int i = 0; i < 8; i++) {
			%>
			<div class="catalog__item">
				<div class="group_content">
					<div class="home-product-item__img"
						style="background-image: url(https://cf.shopee.vn/file/fe2451710511f9ddb24d33dcf1621345);">
					</div>
					<h4 class="home-product-item__name">
						<a
							href="${pageContext.request.contextPath}/default/products/index.jsp">ILLUSION
							T-SHIRT/DARK BROWN</a>

					</h4>
					<h4 class="shop_name">SHOPLANE</h4>
					<div class="home-product-item__rating">
						<ion-icon name="star-outline"></ion-icon>
						<ion-icon name="star-outline"></ion-icon>
						<ion-icon name="star-outline"></ion-icon>
						<ion-icon name="star-outline"></ion-icon>
						<ion-icon name="star-outline"></ion-icon>
					</div>
					<div class="home-product-item__price">
						<span class="home-product-item__price-current">69,000₫</span> <span
							class="home-product-item__price-old">390,000₫</span>
					</div>
				</div>
			</div>
			<%
			}
			%>
			<div class="btn-more">
				<a class="btn-more-link" href="${pageContext.request.contextPath}/">
					XEM TẤT CẢ </a>
			</div>
		</div>
	</div>
</section>

