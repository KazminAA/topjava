<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>List of Meals</title>
        <link href="common.css" rel="stylesheet">
    </head>
<body>
    <table>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th colspan="2">Действие</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <tr 
                <c:choose>
                    <c:when test="${meal.exceed eq true}">
                        class="exceeded"
                    </c:when>
                    <c:otherwise>
                        class="notecxeeded"
                    </c:otherwise>
                </c:choose>
            >
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="dateTime" type="both" />
                    <fmt:formatDate value="${dateTime}" pattern="dd-MM-yy HH:mm" var="fomatedDate" />
                    ${fomatedDate}
                </td>
                <td>
                    ${meal.description}
                </td>
                <td>
                    ${meal.calories}
                </td>
                <td>
                    Редатировать
                </td>
                <td>
                    Удалить
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
