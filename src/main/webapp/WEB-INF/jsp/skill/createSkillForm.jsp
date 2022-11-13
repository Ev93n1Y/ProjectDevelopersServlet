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
     <h3> Add new skill </h3><hr>
        <form action = "/skills" method = "post">
            <label > department: </label>
            <select name="department" style="width:152px">
                <option value="Java">java</option>
                <option value="C++">c++</option>
                <option value="C#">c#</option>
                <option value="JS">js</option>
            </select><br>
            <label > level: </label>
            <select name="level" style="width:152px;margin-left: 45px">
                <option value="Junior">junior</option>
                <option value="Middle">middle</option>
                <option value="Senior">senior</option>
            </select>
            <input type = "submit" name = "method" value = "create"><br><br>
            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>
        </form>
    </body>
</html>