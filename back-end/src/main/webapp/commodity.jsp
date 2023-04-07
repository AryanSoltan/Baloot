<%@ page import="Baloot.Commodity" %>
<%@ page import="Baloot.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Baloot.User" %><%--
  Created by IntelliJ IDEA.
  User: saba
  Date: 4/3/23
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>


<%
  Commodity commodity = (Commodity) request.getAttribute("commodity");
  User user = (User) request.getAttribute("loggedInUser");
  ArrayList<Comment> comments = commodity.getComments();
  ArrayList<Commodity> suggestions = (ArrayList<Commodity>) request.getAttribute("suggestions");
%>

<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Commodity</title>
  <style>
    li {
      padding: 5px;
    }
    table {
      width: 100%;
      text-align: center;
    }
  </style>
</head>
<body>
<span>username: <%=user.getName()%></span>
<br>
<ul>
  <li id="id">Id: <%=commodity.getId()%> </li>
  <li id="name">Name: <%=commodity.getName()%> </li>
  <li id="providerName">Provider Name: <%=commodity.getProviderName()%> </li>
  <li id="price">Price: <%=commodity.getPrice()%> </li>
  <li id="categories">Categories: <%=commodity.getCategories()%> </li>
  <li id="rating">Rating: <%=commodity.getRating()%> </li>
  <li id="inStock">In Stock: <%=commodity.getInStock()%> </li>
</ul>

<label>Add Your Comment:</label>
<form action="" method="post">
  <input type="text" name="comment" value="" />
  <input type="hidden" id="form_action0" name="action" value="comment">
  <button type="submit">submit</button>
</form>
<br>
<form action="" method="POST">
  <label>Rate(between 1 and 10):</label>
  <input type="number" id="quantity" name="quantity" min="1" max="10">
  <input type="hidden" id="form_action1" name="action" value="rate">
  <button type="submit">Rate</button>
</form>
<br>
<form action="" method="POST">
  <input type="hidden" id="form_action2" name="action" value="add">
  <button type="submit">Add to BuyList</button>
</form>
<br />
<table>
  <caption><h2>Comments</h2></caption>
  <tr>
    <th>username</th>
    <th>comment</th>
    <th>date</th>
    <th>likes</th>
    <th>dislikes</th>
  </tr>
  <% for (Comment comment : comments) {%>
  <tr>
    <td> <%= comment.getUserName() %> </td>
    <td> <%= comment.getText() %> </td>
    <td> <%= comment.getDate() %> </td>
    <td>
      <form action="" method="POST">
        <label for=""><%=comment.getLikes()%></label>
        <input
                id="form_comment_id"
                type="hidden"
                name="comment_id"
                value="<%= comment.getId() %>"
        />
        <input type="hidden" id="form_action" name="action" value="like">

        <button type="submit">like</button>
      </form>
    </td>
    <td>
      <form action="" method="POST">
        <label for=""> <%= comment.getDisLikes() %> </label>
        <input
                id="form_comment_id2"
                type="hidden"
                name="comment_id"
                value="<%= comment.getId() %>"
        />
        <input type="hidden" id="form_action4" name="action" value="dislike">
        <button type="submit">dislike</button>
      </form>
    </td>
  </tr>
  <%}%>
</table>
<br><br>
<table>
  <caption><h2>Suggested Commodities</h2></caption>
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
  <%try{ for(Commodity suggestion:suggestions){ %>
  <tr>
    <td> <%=suggestion.getId()%> </td>
    <td> <%=suggestion.getName()%> </td>
    <td> <%=suggestion.getProviderName()%> </td>
    <td> <%=suggestion.getPrice()%> </td>
    <td> <%=suggestion.getCategories()%> </td>
    <td> <%=suggestion.getRating()%> </td>
    <td> <%=suggestion.getInStock()%> </td>
    <td><a href="/commodities/<%= suggestion.getId() %>">Link</a></td>
  </tr>
  <%}}
    catch (Exception e){}
  %>

</table>
</body>
</html>

