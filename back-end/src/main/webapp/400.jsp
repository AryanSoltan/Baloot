<%--
  Created by IntelliJ IDEA.
  User: saba
  Date: 4/3/23
  Time: 11:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String error = (String) request.getAttribute("errorMSG");

%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>400 Error</title>
</head>
<body>
<h1>400<br> <%=error%> </h1>
</body>
</html>
