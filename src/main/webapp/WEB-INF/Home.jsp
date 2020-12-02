<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <title>Home</title>
</head>
<body>
    <p class="text-primary">Hello Dear!</p>
    ${product.getName()}
    <img src="data:image/png;base64,${product.getImg()}">
    <c:choose>
        <c:when test="${reviews.size()>0}">
            <c:forEach items="${reviews}" var="r">
                <p><c:out value="${r}" /></p>
            </c:forEach>
            <c:otherwise>
                <p>There are no Review</p>
            </c:otherwise>
        </c:when>
    </c:choose>
</body>
</html>
