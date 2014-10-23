<%--
  Created by IntelliJ IDEA.
  User: andriybas
  Date: 10/23/14
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Login Success Page</title>
</head>
<body>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) userName = cookie.getValue();
        }
    }
    if (userName == null) response.sendRedirect("index.jsp");
%>
<h3>Hi <%=userName %>, Login successful.</h3>
<br>

<form action="/logout" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
