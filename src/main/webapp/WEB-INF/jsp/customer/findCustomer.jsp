<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/customer/findCustomerForm.jsp"/>
        <c:if test="${not empty customers}">
            <table style="text-align: center" border="1" width="20%">
                <thead>

                        <tr>
                            <td style="text-align: center"><b>id</b></td>
                            <td style="text-align: center"><b>name</b></td>
                            <td style="text-align: center"><b>email</b></td>
                        </tr>
                </thead>
                    <tbody>
                        <c:forEach var = "customer" items="${customers}">
                            <tr>
                                <td style="text-align: center">
                                    <c:out value="${customer.id}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${customer.name}"/>
                                </td>
                                <td style="text-align: center">
                                    <c:out value="${customer.email}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
            </table>
        </c:if>
        <c:if test="${empty customers}">
            <p style="color:red">${message}</p>
        </c:if>
    </body>
</html>