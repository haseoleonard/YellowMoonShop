<%-- 
    Document   : loginrequired
    Created on : Oct 13, 2020, 2:05:03 AM
    Author     : haseo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unauthorized Access</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
        <h1 class="text-center text-muted mt-4 mb-3">HTTP Error 401 - Unauthorized</h1>
        <h4 class="text-center">You Need to login before using this function.</h4>
    </body>
</html>
