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
        <h3>Calculate total developers salary at single project</h3><hr>
        <p>Enter project id</p>
        <form action="/developers">
            <label for="name">id: </label>
            <input type="number" id="id" name="id" min="1" required  style="margin-left: 24px;"/>
            <input type = "submit" name="method" value = "salary"><br><br>
        </form>
        <c:if test="${not empty salary}">
            <table style="text-align: center" border="1" width="20%">
                <thead>
                    <tr>
                        <td style="text-align: center"><b>project id</b></td>
                        <td style="text-align: center"><b>total salary</b></td>
                    </tr>
                    <c:if test="${empty salary}">
                        <p style="color:red">${message}</p>
                    </c:if>
                </thead>
                    <tbody>
                        <tr>
                            <td style="text-align: center">
                                <c:out value="${id}"/>
                            </td>
                            <td style="text-align: center">
                                <c:out value="${salary}"/>
                            </td>
                        </tr>
                    </tbody>
            </table>
        </c:if>
    </body>
</html>