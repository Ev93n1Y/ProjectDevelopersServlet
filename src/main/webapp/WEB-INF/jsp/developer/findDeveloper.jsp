<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/developer/findDeveloperForm.jsp"/>
        <table style="text-align: center" border="1" width="20%">
            <thead>
                <c:if test="${not empty developers}">
                    <tr>
                        <td style="text-align: center"><b>id</b></td>
                        <td style="text-align: center"><b>name</b></td>
                        <td style="text-align: center"><b>age</b></td>
                        <td style="text-align: center"><b>gender</b></td>
                        <td style="text-align: center"><b>salary</b></td>
                    </tr>
                </c:if>
                <c:if test="${empty developers}">
                    <p style="color:red">${message}</p>
                </c:if>
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
    </body>
</html>