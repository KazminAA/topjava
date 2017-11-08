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
            <tr class="${meal.exceed eq true ? "exceeded" : "notexceeded"}">
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
                    <a href="${pageContext.servletContext.contextPath}/meals?oper=edit&id=${meal.id}">Редатировать</a>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/meals?oper=delete&id=${meal.id}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5">
                <form>
                    <input type="button" value="Добавить" onclick="location.href='${pageContext.servletContext.contextPath}/meals?oper=add'"/>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>
