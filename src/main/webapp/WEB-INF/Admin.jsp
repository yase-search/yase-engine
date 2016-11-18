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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<div id="header">
		<div class="wrapper">
			<div class="logo">
				<a href="./"><span>Y</span><span>A</span><span>S</span><span>E</span></a>
			</div>
			<div class="title-back">
				<span>Admin : Liste des sites</span>
			</div>
		</div>
	</div>
	<div class="wrapper search-wrapper">
		<!-- Informations -->
		<p class="sous-title">Nombre de sites web : ${numberWebsite}</p>
		<p class="sous-title">Nombre de pages web : ${numberPages}</p>
		<br/>
		
		<c:forEach var="website" items="${websites}">
			<div class="website">
				<div class="title-website">${website.domain}</div>
				<div class="nbrPage-website">Nombre de page(s) : ${website.numberPage}</div>
			</div>
		</c:forEach>
	</div>
	<script src="assets/js/jquery-3.1.0.min.js"></script>
	<script>
		$(document).ready(function(){
			$(".website").click(function(){
				$(this).toggleClass("open");
			})
		})
	</script>
</body>
</html>
