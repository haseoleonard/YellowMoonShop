<%-- 
    Document   : CakeListPage
    Created on : Oct 1, 2020, 7:40:35 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yellow Moon Store</title>
        <link rel="stylesheet" href="resources/css/jquery-ui.css">
        <style>
            .card-custom {
                overflow: hidden;
                min-height: 450px;
                box-shadow: 0 0 15px rgba(10, 10, 10, 0.3);
            }

            .card-custom-img {
                height: 200px;
                min-height: 200px;
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center;
                border-color: inherit;
            }

            /* First border-left-width setting is a fallback */
            .card-custom-img::after {
                position: absolute;
                content: '';
                top: 161px;
                left: 0;
                width: 0;
                height: 0;
                border-style: solid;
                border-top-width: 40px;
                border-right-width: 0;
                border-bottom-width: 0;
                border-left-width: 545px;
                border-left-width: calc(575px - 5vw);
                border-top-color: transparent;
                border-right-color: transparent;
                border-bottom-color: transparent;
                border-left-color: inherit;
            }

            .card-custom-avatar img {
                border-radius: 50%;
                box-shadow: 0 0 15px rgba(10, 10, 10, 0.3);
                position: absolute;
                top: 100px;
                left: 1.25rem;
                width: 100px;
                height: 100px;
            }
            .page-item.active .page-link{
                background-color: #ffa64d !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"/>
        <h2 class="text-center mb-3 mt-3">Product List</h2>
        <form style="width: 500px; margin: 0 auto" action="search" method="POST">
            <div class="d-flex justify-content-between">
                <input style="width: 220px" type="text" name="txtSearchValue" value="${param.txtSearchValue}"/>
                <select style="width: 180px" name="txtCategory">
                    <option></option>
                    <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                        <option <c:if test="${param.txtCategory eq category.categoryName}">selected="true"</c:if>>
                            ${category.categoryName}
                        </option>
                    </c:forEach>
                </select>
                <input class="btn btn-success" type="submit" name="btAction" value="Search"/>
            </div>

            <div class="d-flex justify-content-between">
                <input type="text" name="minPrice" id="minPrice" readonly="true" style="border:0; color:#f6931f; font-weight:bold;"/>
                <input class="text-right" type="text" name="maxPrice" id="maxPrice" readonly="true" style="border:0; color:#f6931f; font-weight:bold;"/>
            </div>
            <div id="slider-range"></div>            
        </form>
        <c:if test="${not empty requestScope.PRODUCT_LIST}">
            <div class="mt-5 container">
                <div class="row">
                    <c:forEach var="product" items="${requestScope.PRODUCT_LIST}">
                        <div class="col-md-3">
                            <div class="card card-custom bg-white border-white">
                                <div class="card-custom-img" style="background-image: url(resources/imgs/${product.image})"></div>
                                <div class="card-body" style="overflow-y: auto">
                                    <h4 class="card-title">${product.productName}</h4>
                                    <p class="card-text">${product.description}</p>
                                </div>
                                <div class="card-footer" style="background: inherit; border-color: inherit;">
                                    <div class="justify-content-between d-flex">
                                        <p class="card-text">${product.price} VND</p>
                                        <p class="card-text">In Stock: ${product.quantity}</p>
                                    </div>
                                    <form action="addToCart" method="POST">
                                        <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                                        <input type="hidden" name="minPrice" value="${param.minPrice}"/>
                                        <input type="hidden" name="maxPrice" value="${param.maxPrice}"/>
                                        <input type="hidden" name="txtCategory" value="${param.txtCategory}"/>
                                        <input type="hidden" name="page" value="${param.page}"/>
                                        <input type="hidden" name="txtProductID" value="${product.productID}"/>
                                        <input type="submit" name="btAction" class="btn btn-warning" value="Add To Cart"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!---->
            <ul class="mt-2 justify-content-center pagination pagination-lg text-center">
                <c:forEach begin="1" end="${requestScope.MAX_PAGE}" varStatus="counter">
                    <c:url var="paging" value="search">
                        <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                        <c:param name="txtCategory" value="${param.txtCategory}"/>
                        <c:if test="${not empty param.minPrice}"><c:param name="minPrice" value="${param.minPrice}"/></c:if>
                        <c:if test="${empty param.minPrice}"><c:param name="minPrice" value="0"/></c:if>
                        <c:if test="${not empty param.maxPrice}"><c:param name="minPrice" value="${param.maxPrice}"/></c:if>
                        <c:if test="${empty param.maxPrice}"><c:param name="minPrice" value="${requestScope.MAX_PRICE}"/></c:if>
                        <c:param name="page" value="${counter.count}"/>
                    </c:url>
                    <li class="page-item <c:if test="${counter.count==requestScope.CURR_PAGE}">active</c:if>">                        
                        <a class="page-link" href="${pageScope.paging}">
                            ${counter.count}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty requestScope.PRODUCT_LIST}">
            <h3 class="text-center mt-4 text-muted">No Product Found!</h3>
        </c:if>
        <script>
            var minPrice = "${param.minPrice}";
            var maxPrice = "${param.maxPrice}";
            if (minPrice === null)
                minPrice = 0;
            if (maxPrice === null || maxPrice.trim() === "")
                maxPrice =${requestScope.MAX_PRICE};

            $(function () {
                $("#slider-range").slider({
                    range: true,
                    min: 0,
                    max: ${requestScope.MAX_PRICE},
                    values: [minPrice, maxPrice],
                    step: 1000,
                    slide: function (event, ui) {
                        $("#minPrice").val(ui.values[0]);
                        $("#maxPrice").val(ui.values[1]);
                    }
                }
                );
                $("#minPrice").val($("#slider-range").slider("values", 0));
                $("#maxPrice").val($("#slider-range").slider("values", 1));
            });
        </script>    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </body>
</html>
