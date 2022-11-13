<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
     <c:import url="${contextPath}/WEB-INF/jsp/navigation.jsp"/>
     <h3> Add new developer </h3><hr>
        <form action = "/developers" method = "post">
            <label > name: </label>
            <input type = "text" id = "name" name = "name" required style="margin-left: 13px;"/><br>
            <label> age: </label>
            <input type = "number" id = "age" name = "age" min="0" required  style="margin-left: 25px;"/><br>
            <label > gender: </label>
            <select name="gender" style="width:152px">
                <option value="male">male</option>
                <option value="female">female</option>
            </select><br>
            <label> salary: </label>
            <input type = "number" id = "salary" name = "salary" min="0" required  style="margin-left: 10px;"/>
            <input type = "submit" name = "method" value = "create"><br><br>
            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>
        </form>
    </body>
</html>