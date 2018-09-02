<%--
  Created by IntelliJ IDEA.
  User: wincher
  Date: 2018/8/23
  Time: 02:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Customer - Manage</title>
</head>
<body>
    <h1>Customer List View</h1>

    <table>
        <tr>
            <th>name</th>
            <th>contact</th>
            <th>tel</th>
            <th>email</th>
            <th>operation</th>
        </tr>
        <c:forEach var="customer" items="${customerList}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.contact}</td>
                <td>${customer.telephone}</td>
                <td>${customer.email}</td>
                <td>
                    <a href ="${BASE}/customer_edit?id=${customer.id}">edit</a>
                    <a href ="${BASE}/customer_delete?id=${customer.id}">del</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
