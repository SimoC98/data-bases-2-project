<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>registration page</title>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/css/myStyle.css" />
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/css/bootstrap.css" />
</head>
<body>
<h1 class="title text-center p-3">Registration</h1>
<div class="d-flex justify-content-center">
    <form action="<c:url value="/register"/>" class="align-middle w-25 pt-5" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" class="form-control" id="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" required>
        </div>
        <div class="form-group">
            <label for="password_conf">Confirm Password:</label>
            <input type="password" class="form-control" id="password_conf" required>
        </div>
        <input class="btn btn-primary w-100" type="submit" value="Sign up">
    </form>
</div>
</body>
</html>