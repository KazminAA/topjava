<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<li class="dropdown">
    <a  href="#" class="dropdown-toggle" data-toggle="dropdown">
        ${pageContext.response.locale}
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a></li>
    </ul>
</li>
