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
     <h3> Add new project </h3><hr>
        <form action = "/projects" method = "post">
            <label > name: </label>
            <input type = "text" id = "name" name = "name" required style="margin-left: 59px;"/><br>
            <label> company id: </label>
            <input type = "number" id = "company id" name = "company id" min="1" required  style="margin-left: 14px;"/><br>
            <label > customer id: </label>
            <input type = "number" id = "customer id" name = "customer id" min="1" required  style="margin-left: 10px;"/><br>
            <label > cost: </label>
            <input type = "number" id = "cost" name = "cost" min="0" required  style="margin-left: 67px;"/><br>
            <label> creation date: </label>
            <input type = "date" id = "creation date" name = "creation date" required style="width:152px"/>
            <input type = "submit" name = "method" value = "create"><br><br>
            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>
        </form>
    </body>
</html>