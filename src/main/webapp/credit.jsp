<%@ page import="Baloot.User" %><%--
  Created by IntelliJ IDEA.
  User: saba
  Date: 4/3/23
  Time: 7:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  User user = (User) request.getAttribute("loggedInUser");

%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Credit</title>
</head>

<body>
<form method="post" action="">
  <label>Credits:</label>
  <input name="credit" type="text" />
  <br>
  <button type="submit">Add credits</button>
</form>
</body>
</html>
