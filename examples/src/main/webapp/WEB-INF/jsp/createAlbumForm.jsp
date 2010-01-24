<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/WEB-INF/error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add album to ${artist}</title>
</head>
<body>
<h1>Add album to ${artist}</h1>
<spring:form  action="createAlbum.do">
	<table>
		<tr>
			<td>Name:</td>
			<td><spring:input path="name"/></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><spring:input path="description"/></td>
		</tr>
		<tr>
			<td>Album Cover URL:</td>
			<td><spring:input path="cover"/></td>
		</tr>
		
	</table>
	<spring:hidden path="artistId"/>
	<input type="submit"/>
</spring:form>
</body>
</html>