<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Roles</title>

</head>
<body>

<div class="form">
    <a href="<c:url value='/' />">Назад</a>
    <h1>Управление ролями</h1><br>
    <form method="post" action="<c:url value="/roles"/>">

        <input type="text" required placeholder="username" name="name">
        <input type="text" required placeholder="role" name="role"><br>
        <input class="button" type="submit" value="Изменить">
    </form>
</div>
<hr />
<c:out value="Список пользователей"/><br>
<br>
<c:forEach items="${requestScope.userList}" var="user">
    <c:out value="${user}"/><br>
</c:forEach>
</body>
</html>