<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download</title>

</head>
<body>

<div class="form">
    <h1>Скачать файл</h1><br>
    <form method="get" action="<c:url value="/download"/>">

        <input type="text" required placeholder="path" name="path"><br>
        <input class="button" type="submit" value="Скачать">
    </form>
</div>
</body>
</html>