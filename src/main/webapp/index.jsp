<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>login</title>
</head>
<body>
    <h1 class="title text-center p-3">Gamified Marketing App</h1>
    <div class="d-flex justify-content-center">
        <form action="<c:url value="/login"/>" class="align-middle w-25 pt-5" method="POST">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" required>
            </div>
            <input class="btn btn-primary w-100" type="submit" value="Login">
            <a href="<c:url value="/register"/>" class="btn btn-light w-100" role="button">Register</a>
        </form>
    </div>
</body>
</html>