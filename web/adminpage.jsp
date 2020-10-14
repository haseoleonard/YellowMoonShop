<%-- 
    Document   : adminpage
    Created on : Oct 5, 2020, 9:40:35 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yellow Moon Admin Interface</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
            <h1 class="text-center text-muted">Product Management Interface</h1>

            <form class="text-center" action="searchAdmin" method="POST">
                <h3> Search Product </h3>
                <input style="width: 400px" class="mb-3" type="text" name="txtSearchValue" value="${param.txtSearchValue}"/><br/>
            <input class="btn btn-warning" type="submit" name="btAction" value="Search"/>
            <a href="createproduct.jsp" class="btn btn-success">Create Product</a>
        </form>

            <div style="padding: 0 40px; margin-top: 20px">
            <c:if test="${not empty requestScope.PRODUCT_LIST}">
                <table class="table table-hover table-warning">
                    <thead class="thead-light">
                        <tr>
                            <th>Product Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Image</th>
                            <th>Create Date</th>
                            <th>Expiration Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${requestScope.PRODUCT_LIST}">
                            <tr style="cursor: pointer" onclick="loadProduct(${product.productID})">
                                <td>${product.productName}</td>
                                <td>${product.description}</td>
                                <td>${product.price}VND</td>
                                <td>${product.quantity}</td>
                                <td>
                                    <c:if test="${not empty product.image}">
                                        <img src="resources/imgs/${product.image}" height="200" width="400"/>
                                    </c:if>
                                </td>
                                <td>${product.createDate}</td>
                                <td>${product.expirationDate}</td>
                                <td>${product.statusID}</td>  
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>      
                <ul class="mt-2 justify-content-center pagination pagination-lg text-center">
                    <c:forEach begin="1" end="${requestScope.MAX_PAGE}" varStatus="counter">
                        <c:url var="paging" value="searchAdmin">
                            <c:param name="txtSearchValue" value="${param.txtSearchValue}"></c:param>
                            <c:param name="page" value="${counter.count}"></c:param>
                        </c:url>
                        <li class="page-item <c:if test="${counter.count==requestScope.CURR_PAGE}">active</c:if>">                        
                            <a class="page-link" href="${paging}">
                                ${counter.count}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <script>
            loadProduct = (productID) => {
                window.location.href = `loadProduct?txtProductID=` + productID;
            }
        </script>
    </body>
</html>
