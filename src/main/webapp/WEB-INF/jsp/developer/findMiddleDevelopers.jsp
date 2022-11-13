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
        <h3>All middle developers </h3><hr>
        <form action="/developers">
            <input type = "submit" name="method" value = "Find middle devs"><br><br>
        </form>
        <c:if test="${not empty developers}">
            <table style="text-align: center" border="1" width="20%">
                <thead>
                    <tr>
                        <td style="text-align: center"><b>id</b></td>
                        <td style="text-align: center"><b>name</b></td>
                        <td style="text-align: center"><b>age</b></td>
                        <td style="text-align: center"><b>gender</b></td>
                        <td style="text-align: center"><b>salary</b></td>
                    </tr>
                </thead>
                    <tbody>
                        <c:forEach var = "developer" items="${developers}">
                            <tr>
                                <td style="text-align: center">
                                    <c:out value="${developer.id}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${developer.name}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${developer.age}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${developer.gender}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${developer.salary}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
            </table>
        </c:if>
        <c:if test="${empty developers}">
            <p style="color:red">${message}</p>
        </c:if>
    </body>
</html>