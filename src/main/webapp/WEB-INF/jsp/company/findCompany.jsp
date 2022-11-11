<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/company/findCompanyForm.jsp"/>
        <table style="text-align: center" border="1" width="20%">
            <thead>
                <c:if test="${not empty companies}">
                    <tr>
                        <td style="text-align: center"><b>id</b></td>
                        <td style="text-align: center"><b>name</b></td><b>
                        <td style="text-align: center"><b>location</b></td><b>
                    </tr>
                </c:if>
                <c:if test="${empty companies}">
                    <p style="color:red">${message}</p>
                </c:if>
            </thead>
                <tbody>
                    <c:forEach var = "company" items="${companies}">
                        <tr>
                            <td style="text-align: center;">
                                <c:out value="${company.id}"/>
                            </td>
                            <td style="text-align: center">
                                <c:out value="${company.name}"/>
                            </td>
                            <td style="text-align: center">
                                <c:out value="${company.location}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
        </table>
    </body>
</html>