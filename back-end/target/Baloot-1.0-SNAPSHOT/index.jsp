<%@ page import="Baloot.Managers.UserManager" %>
<%@ page import="Baloot.User" %>

<%
    User user = (User) request.getAttribute("loggedInUser");

%>

<!DOCTYPE html>

<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<ul>
    <li id="email">username: <%=user.getName()%> </li>
    <li>
        <a href="/commodities">Commodities</a>
    </li>
    <li>
        <a href="/buylist">Buy List</a>
    </li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>
    <li>
        <a href="/logout">Log Out</a>
    </li>
</ul>

</body></html>