<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="content-type" charset="UTF-8" content="text/html">
    <title>Add/Edit Meal</title>
    <link href="editmeal.css" rel="stylesheet"/>
<body>
<div class="parent">
    <div class="block">
        <form id="meal-info" name="Edit Meal" action="${pageContext.servletContext.contextPath}/meals?oper=save" method="post" accept-charset="UTF-8">
        <c:choose>
            <c:when test="${meal ne null}">
                <input type="hidden" name="id" value="${meal.id}"/>
                <c:set var="dt" value="${mealDateTime}" />
                <c:set var="desc" value="${meal.description}" />
                <c:set var="cal" value="${meal.calories}" />
            </c:when>
            <c:otherwise>
                <c:set var="dt" value="0" />
                <c:set var="desc" value="0" />
                <c:set var="cal" value="0" />
            </c:otherwise>
        </c:choose>
                <label>Дата:</label><input type="datetime-local" name="date" value="${meal.dateTime}" class="placeholder" required/>
                <label>Описание:</label><input type="text" name="descr" value="${desc}" class="placeholder" />
                <label>Калории:</label><input type="number" name="calories" min="0" step="0.1" value="${cal}" class="placeholder" /><br>
                <input type="submit" value="Action">
        </form>
    </div>
</div>
</body>
</html>
