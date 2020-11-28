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
        <form action="<c:url value="/login"/>" id="login" class="align-middle w-25 pt-5" method="POST">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" required>
            </div>
            <input class="btn btn-primary w-100" type="submit" value="Login">
            <button onclick="show()" class="btn btn-light w-100" role="button">Register</button>
        </form>

        <form action="<c:url value="/register"/>" id="registration" class="align-middle w-25 pt-5" method="POST" style="display: none;">
            <div class="form-group">
                <label for="user">Username:</label>
                <input type="text" class="form-control" id="user" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" class="form-control" id="email" required>
            </div>
            <div class="form-group">
                <label for="pass">Password:</label>
                <input type="password" class="form-control" id="pass" required>
            </div>
            <div class="form-group">
                <label for="pass_conf">Confirm Password:</label>
                <input type="password" class="form-control" id="pass_conf" required>
            </div>
            <input class="btn btn-primary w-100" type="submit" value="Sign up">
            <button onclick="show()" class="btn btn-light w-100" role="button">Go to Login</button>
        </form>
    </div>
<script >
    function show() {
        var x = document.getElementById("login");
        var y = document.getElementById("registration");
        if (x.style.display === "none") {
            x.style.display = "block";
            y.style.display = "none";
        } else {
            x.style.display = "none";
            y.style.display = "block";
        }

    }

</script>
</body>
</html>