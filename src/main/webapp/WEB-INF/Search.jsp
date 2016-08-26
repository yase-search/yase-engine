<%@ page import="java.util.regex.Pattern" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link rel="icon" href='<c:url value="/assets/img/yase.png" />' />
</head>
<body>
	<div id="header">
		<div class="wrapper">
			<div class="logo">
				<a href="/" title="Home"><span>Y</span><span>A</span><span>S</span><span>E</span></a>
			</div>
			<form method="get" action="<c:url value="/yolo" />" class="search-input-wrapper" id="search-form">
			<input type="text" name="search" placeholder="Search..." value="<c:out value="${search.input}" />">
			<div id="search-button-wrapper">
				<a class="btn" href="#" onclick="document.getElementById('search-form').submit();" id="search-button"><i class="fa fa-search"></i></a>
			</div>
		</form>
		</div>
	</div>
	<div id="subheader">
		<i class="fa fa-globe"></i> Sur le web <span class="count">(<c:out value="${fn:length(search.pages)}" /> résultat<c:out value="${fn:length(search.pages) > 1 ? 's' : null }" />)</span>
	</div>
	<div class="wrapper search-wrapper">
		<c:forEach var="page" items="${search.pages}">
			<div class="result">
				<div class="row">
					<div class="col-xs-1" style="padding-right:0;">
						<c:choose>
							<c:when test="${page.faviconUrl != null}">
								<img src="${page.faviconUrl}" style="max-width:100%;">
							</c:when>
							<c:otherwise>
								<span class="fa-stack fa-lg no-favicon">
								  <i class="fa fa-picture-o fa-stack-1x"></i>
								  <i class="fa fa-ban fa-stack-2x"></i>
								</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-xs-11">
						<a href="<c:out value="${page.url}" />" class="title" target="_blank">${searchFormat.boldKeywords(fn:escapeXml(page.title), fn:escapeXml(search.input))}</a>
						<p class="url">
								${searchFormat.boldKeywords(fn:escapeXml(page.url), fn:escapeXml(search.input))}
						</p>
						<p class="description">
								${searchFormat.boldKeywords(searchFormat.pertinentExtract(fn:escapeXml(page.description), page.content, fn:escapeXml(search.input)), fn:escapeXml(search.input))}
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>
