<%--
  Created by IntelliJ IDEA.
  User: saba
  Date: 4/3/23
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="Baloot.Managers.UserManager" %>
<%@page import="Baloot.Managers.CommodityManager" %>
<%@ page import="Baloot.Commodity" %>
<%@ page import="Baloot.User" %>
<%@ page import="java.util.ArrayList" %>

<%
    User user = (User) request.getAttribute("loggedInUser");
    ArrayList<Commodity> commodities = (ArrayList<Commodity>) request.getAttribute("commodities");

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<p id="username">username: <%=user.getName()%> </p>
<br><br>
<form action="" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_category">Search By Cagtegory</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Rate</button>
</form>
<br><br>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>

    <% for(Commodity commodity: commodities) { %>

    <tr>
        <td><%=commodity.getId()%></td>
        <td><%=commodity.getName()%></td>
        <td><%=commodity.getProviderName()%></td>
        <td><%=commodity.getPrice()%></td>
        <td><%=commodity.getCategories()%></td>
        <td><%=commodity.getRating()%></td>
        <td><%=commodity.getInStock()%></td>
        <td><a href="/commodities/<%=commodity.getId()%>">Link</a></td>
    </tr>
    <% } %>

</table>
</body>
</html>