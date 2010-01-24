<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/WEB-INF/error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>List of artists</title>
</head>
<body>
	<h1>Artists</h1>
	<table>
		<tr><th>Name</th></tr>
		<c:forEach items="${artists}" var="artist">
			<tr>
				<td>
					<a href="artist.do?id=${artist.id}">${artist}</a> 
				</td>
			</tr> 
		</c:forEach>
	</table>
</body>
</html>