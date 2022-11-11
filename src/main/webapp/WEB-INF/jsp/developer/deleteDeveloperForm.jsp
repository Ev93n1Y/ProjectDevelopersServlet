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
     <h3>Delete developer</h3><hr>
        <form action="/developers" method = "post">
            <label for="name">id: </label>
            <input type="number" id="id" name="id" min="1" required/>
            <input type = "submit" name="method" value = "delete"><br><br>
            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>
        </form>
    </body>
</html>