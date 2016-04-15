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
			<div class="search-input-wrapper">
				<input type="text" name="search" value="Search">
				<div id="search-button-wrapper">
					<a class="btn" href="#" id="search-button"><i
						class="fa fa-search"></i></a>
				</div>
			</div>
		</div>
	</div>
	<div class="wrapper search-wrapper">
		<c:forEach var="page" items="${results}">
			<div class="result">
				<a href="<c:out value="${page.value.url}" />" class="title"><c:out
						value="${page.value.title}" /></a>
				<p class="url">
					<c:out value="${page.value.url}" />
				</p>
				<p class="description">
					<c:out value="${page.value.description}" />
				</p>
			</div>
		</c:forEach>
	</div>
</body>
</html>