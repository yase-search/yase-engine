<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>YASE</title>
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:400,500,300,700">
<link rel="stylesheet" type="text/css"
	href='<c:url value="/assets/css/reset.css" />'>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/assets/css/yase.css" />'>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
</head>
<body>
	<div id="header">
		<div class="wrapper">
			<div class="logo">
				<span>Y</span><span>A</span><span>S</span><span>E</span>
			</div>
			<form method="get" action="<c:url value="/yolo" />" class="search-input-wrapper" id="search-form">
			<input type="text" name="search" placeholder="Search..." value="<c:out value="${search.input}" />">
			<div id="search-button-wrapper">
				<a class="btn" href="#" onclick="document.getElementById('search-form').submit();" id="search-button"><i class="fa fa-search"></i></a>
			</div>
		</form>
		</div>
	</div>
	<div class="wrapper search-wrapper">
		<c:forEach var="page" items="${search.pages}">
			<div class="result">
				<a href="<c:out value="${page.website.protocol}://${page.website.domain}${page.url}" />" class="title" target="_blank"><c:out
						value="${page.title}" /></a>
				<p class="url">
					<c:out value="${page.website.domain}${page.url}" />
				</p>
				<p class="description">
					<c:out value="${page.description}" />
				</p>
			</div>
		</c:forEach>
	</div>
</body>
</html>