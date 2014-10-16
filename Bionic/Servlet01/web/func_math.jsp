<%--
  Created by IntelliJ IDEA.
  User: andriybas
  Date: 10/16/14
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hi title</title>
</head>
<body>
<%
    String func = request.getParameter("func");
    double val = Double.parseDouble(request.getParameter("val"));
    double res = 0.0;
    if ("sin".equals(func)) {
        res = java.lang.Math.sin(val);
    } else if ("cos".equals(func)) {
        res = java.lang.Math.cos(val);
    } else if ("tan".equals(func)) {
        res = java.lang.Math.tan(val);
    }

    response.getWriter().print(func + " ( " + val + " ) = " + res);
%>
</body>
</html>
