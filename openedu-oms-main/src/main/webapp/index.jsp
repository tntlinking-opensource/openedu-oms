<%@ page import="org.beangle.web.util.CheckMobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<script>
    <%if(CheckMobile.check(request)){
    %>
    location.href = "<%=request.getContextPath()%>/login.action";
    <%
    }else{
    %>
    <%--location.href = "<%=request.getContextPath()%>/web/index.action";--%>
    location.href = "<%=request.getContextPath()%>/web/index2.action";
    <%
    }
    %>
</script>
</body>
</html>
