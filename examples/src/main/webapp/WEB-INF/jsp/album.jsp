<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/WEB-INF/error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Album: ${topic.name}</title>
</head>
<body>
	<img src="${topic.cover}" style="float:right;"/>
	<h1>${topic.name}</h1>
	<h2>by <a href="artist.do?id=${topic.artist.id}">${topic.artist}</a></h2>
	${topic.description}

	<br clear="all"/>	
	<h2>Other albums by ${topic.artist}</h2>
	<table>
		<tr><th>Title</th><th>Description</th></tr>
		<c:forEach items="${topic.artist.albums}" var="album">
			<c:if test="${album.id != topic.id }">
				<tr>
					<td><a href="album.do?id=${album.id}">${album}</a></td>
					<td>${album.description}</td>
				</tr>
			</c:if> 
		</c:forEach>
	</table>
</body>
</html>