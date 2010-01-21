<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/WEB-INF/error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Artist: ${topic.name}</title>
</head>
<body>
	<h1>${topic.name}</h1>
	${topic.description}
	<h2>Albums:</h2>
	<table>
		<tr><th>Title</th><th>Description</th></tr>
		<c:forEach items="${topic.albums}" var="album">
			<tr><td>${album}</td><td>${album.description}</td></tr> 
		</c:forEach>
	</table>
	<a href="createAlbum.do">Add a new album</a>
</body>
</html>