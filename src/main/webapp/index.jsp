<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>YASE</title>
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:400,500,300,700">
	<link rel="stylesheet" type="text/css" href='<c:url value="/assets/css/reset.css" />'>
	<link rel="stylesheet" type="text/css" href='<c:url value="/assets/css/yase.css" />'>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link rel="icon" href='<c: url value="/assets/img/yase.png" />' />
</head>
<body>
	<div class="wrapper">
		<div class="logo">
		<span>Y</span><span>A</span><span>S</span><span>E</span>
		</div>
		<div class="sublogo">Yet another search engine</div>
		<form method="get" action="<c:url value="/yolo" />" class="search-input-wrapper" id="search-form">
			<input type="text" name="search" placeholder="Search...">
			<div id="search-button-wrapper">
				<a class="btn" href="#" onclick="document.getElementById('search-form').submit();" id="search-button"><i class="fa fa-search"></i></a>
			</div>
		</form>
	</div>
</body>
</html>
