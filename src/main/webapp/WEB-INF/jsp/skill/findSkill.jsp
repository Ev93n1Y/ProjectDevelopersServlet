<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/developer/findDeveloperForm.jsp"/>
        <table style="text-align: center" border="1" width="20%">
            <thead>
                <c:if test="${not empty skills}">
                    <tr>
                        <td style="text-align: center"><b>id</b></td>
                        <td style="text-align: center"><b>department</b></td>
                        <td style="text-align: center"><b>level</b></td>
                    </tr>
                </c:if>
                <c:if test="${empty skills}">
                    <p style="color:red">${message}</p>
                </c:if>
            </thead>
                <tbody>
                    <c:forEach var = "skill" items="${skills}">
                        <tr>
                            <td style="text-align: center">
                                <c:out value="${skill.id}"/>
                            </td>
                            <td style="text-align: center">
                                <c:out value="${skill.department}"/>
                            </td>
                            <td style="text-align: center">
                                <c:out value="${skill.level}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
        </table>
    </body>
</html>