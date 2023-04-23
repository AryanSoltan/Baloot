<%@ page import="Baloot.User" %>
<%@ page import="Baloot.BuyList" %>
<%@ page import="Baloot.Commodity" %><%--
  Created by IntelliJ IDEA.
  User: saba
  Date: 4/3/23
  Time: 8:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) request.getAttribute("user");
    BuyList buylist = (BuyList) request.getAttribute("buylist");
%>

<html lang="en"><head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<ul>
    <li id="username">Username: <%=user.getName()%> </li>
    <li id="email">Email: <%=user.getEmail()%> </li>
    <li id="birthDate">Birth Date: <%=user.getBirthDate()%> </li>
    <li id="address"><%=user.getAddress()%> </li>
    <li id="credit">Credit: <%=user.getCredit()%> </li>
    <li>Current Buy List Price:  <%=buylist.getBuylistPrice()%> </li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>

    <li>
        <label>Enter discount code:</label>
        <form action="" method="post">
            <input type="text" name="discount" value="" />
            <input type="hidden" id="form_action0" name="action" value="discount">
            <button type="submit">apply</button>
        </form>
    </li>
    <li>
        <form action="" method="POST">
            <label>Submit & Pay</label>
            <input id="form_payment" type="hidden" name="userId" value=<%=user.getName()%>>
            <input type="hidden" id="form_action" name="action" value="submit">
            <button type="submit">Payment</button>
        </form>
    </li>
</ul>
<table>
    <caption>
        <h2>Buy List</h2>
    </caption>
    <tbody><tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th></th>
        <th></th>
    </tr>
    <% try { for(Commodity commodity : buylist.getAllCommodities()) {%>
    <tr>
        <td> <%=commodity.getId()%> </td>
        <td><%=commodity.getName()%></td>
        <td> <%=commodity.getProviderName()%> </td>
        <td> <%=commodity.getPrice()%> </td>
        <td> <%=commodity.getCategories()%> </td>
        <td> <%=commodity.getRating()%> </td>
        <td> <%=commodity.getInStock()%> </td>
        <td><a href="/commodities/<%=commodity.getId()%>">Link</a></td>
        <td>
            <form action="" method="POST">
                <input id="form_commodity_id" type="hidden" name="commodityId" value=<%=commodity.getId()%>>>
                <input type="hidden" id="form_action1" name="action" value="remove">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <% }} catch (Exception e){}%>

    </tbody></table>
</body></html>
