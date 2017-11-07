<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add/Edit Meal</title>
    <link href="common.css" rel="stylesheet"/>
<body>
<div class="parent">
    <div class="block">
        <c:choose>
            <c:when test="${meal ne null}">
                <form name="Edit Meal" action="/mealedit">
                <input type="hidden" name="id" value="${meal.id}"/>
                <c:set var="dt" value="${mealDateTime}" />
                <c:set var="desc" value="${meal.description}" />
                <c:set var="cal" value="${meal.calories}" />
            </c:when>
            <c:otherwise>
                <form name="Add Meal" action="/mealadd">
                <c:set var="dt" value="0" />
                <c:set var="desc" value="0" />
                <c:set var="cal" value="0" />
            </c:otherwise>
        </c:choose>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="dateTime" type="both" />
                <fmt:formatDate value="${dateTime}" pattern="dd.MM.yyyy HH:mm" var="fomatedDate" />
                <input type="text" name="date" value="${fomatedDate}"/>
                <input type="text" name="descr" value="${desc}" />
                <input type="text" name="calories" value="${cal}">
                <input type="submit" value="Action">
        </form>
    </div>
</div>
</body>
</html>
