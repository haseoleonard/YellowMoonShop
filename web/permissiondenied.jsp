<%-- 
    Document   : permissiondenied
    Created on : Oct 13, 2020, 2:02:23 AM
    Author     : haseo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Permission Denied</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
        <h1 class="text-center text-muted mt-4 mb-4">Error 403 - Permission Denied</h1>
        <div class="alert alert-danger ml-5 mr-5">
            You don't Have the Privilege to access this resource!
        </div>
    </body>
</html>
