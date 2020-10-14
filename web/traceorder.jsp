<%-- 
    Document   : traceorder
    Created on : Oct 9, 2020, 2:50:21 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tracking Order</title>
        <style>
            .payment-form{
                padding-bottom: 50px;
                font-family: 'Montserrat', sans-serif;
            }

            .payment-form.dark{
                background-color: #f6f6f6;
            }

            .payment-form .content{
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.075);
                background-color: white;
            }

            .payment-form .block-heading{
                padding-top: 50px;
                margin-bottom: 40px;
                text-align: center;
            }

            .payment-form .block-heading p{
                text-align: center;
                max-width: 420px;
                margin: auto;
                opacity:0.7;
            }

            .payment-form.dark .block-heading p{
                opacity:0.8;
            }

            .payment-form .block-heading h1,
            .payment-form .block-heading h2,
            .payment-form .block-heading h3 {
                margin-bottom:1.2rem;
                color: #3b99e0;
            }

            .payment-form form{
                border-top: 2px solid #ffa64d;
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.075);
                background-color: #ffffff;
                padding: 0;
                max-width: 100%;
                margin: auto;
            }

            .payment-form .title{
                font-size: 1em;
                border-bottom: 1px solid rgba(0,0,0,0.1);
                margin-bottom: 0.8em;
                font-weight: 600;
                padding-bottom: 8px;
            }

            .payment-form .products{
                background-color: #f7fbff;
                padding: 25px;
            }

            .payment-form .products .item{
                margin-bottom:1em;
            }

            .payment-form .products .item-name{
                font-weight:600;
                font-size: 0.9em;
            }

            .payment-form .products .item-description{
                font-size:0.8em;
                opacity:0.6;
            }

            .payment-form .products .item p{
                margin-bottom:0.2em;
            }

            .payment-form .products .price{
                float: right;
                font-weight: 600;
                font-size: 0.9em;
            }

            .payment-form .products .total{
                border-top: 1px solid rgba(0, 0, 0, 0.1);
                margin-top: 10px;
                padding-top: 19px;
                font-weight: 600;
                line-height: 1;
            }

            .payment-form .card-details{
                padding: 25px 25px 15px;
            }

            .payment-form .card-details label{
                font-size: 12px;
                font-weight: 600;
                margin-bottom: 15px;
                color: #79818a;
                text-transform: uppercase;
            }

            .payment-form .card-details button{
                margin-top: 0.6em;
                padding:12px 0;
                font-weight: 600;
            }

            .payment-form .date-separator{
                margin-left: 10px;
                margin-right: 10px;
                margin-top: 5px;
            }

            @media (min-width: 576px) {
                .payment-form .title {
                    font-size: 1.2em; 
                }

                .payment-form .products {
                    padding: 40px; 
                }

                .payment-form .products .item-name {
                    font-size: 1em; 
                }

                .payment-form .products .price {
                    font-size: 1em; 
                }

                .payment-form .card-details {
                    padding: 40px 40px 30px; 
                }

                .payment-form .card-details button {
                    margin-top: 2em; 
                } 
            }
        </style>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp"></jsp:include>
        <c:if test="${not empty requestScope.CHECKOUT_SUCCESS}">
            <br/>
            <div class="text-center text-muted alert alert-success">
                Your Order Have Been Succesfully Process. Your OrderID is: ${requestScope.CHECKOUT_SUCCESS}
            </div>
        </c:if>
        <c:if test="${not empty requestScope.CHECKOUT_FAILED}">
            <div class="text-center text-muted alert alert-danger alert-dismissible fade show" role="alert">
                You have failed to Pay With Momo. Your OrderID: <strong>${requestScope.CHECKOUT_FAILED}</strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.LOGIN_USER}">
            <h1 class="text-center pt-3 mb-0 payment-form">Tracking Order</h1>        
            <form action="traceOrder" method="POST" class="text-center payment-form">
                Enter Your Order ID <input type="text" name="txtOrderID" required="true" maxlength="20" value="${param.txtOrderID}"/>
                <input type="submit" name="btAction" value="Check Order"/>
            </form>
            <c:if test="${not empty param.txtOrderID and not empty requestScope.ORDER}">
                <section class="payment-form pt-4">
                    <div class="container">
                        <div class="products">
                            <h3>Details For Order: ${requestScope.ORDER.orderID}</h3>
                            <div class="card-details">
                                <strong>Customer Name: </strong>${requestScope.ORDER.customerName}<br/>
                                <strong>Customer Address: </strong>${requestScope.ORDER.customerAddress}<br/>
                                <strong>Phone Number: </strong>${requestScope.ORDER.customerPhone}<br/>
                                <strong>Order Date: </strong>${requestScope.ORDER.orderDate}<br/>
                                <strong>Payment Method: </strong>${requestScope.ORDER.paymentMethodName}<br/>
                                <div class="d-flex">
                                    <strong>Payment Status:  
                                        <span class="
                                              <c:if test="${requestScope.ORDER.paymentStatus eq 0}">badge badge-danger</c:if>
                                              <c:if test="${requestScope.ORDER.paymentStatus eq 1}">badge badge-warning</c:if>
                                              <c:if test="${requestScope.ORDER.paymentStatus eq 2}">badge badge-success</c:if>
                                                  ">
                                              ${requestScope.ORDER.paymentStatusName}
                                        </span>
                                    </strong>
                                </div>
                                <c:if test="${requestScope.ORDER.paymentMethod eq 1}">
                                    <c:if test="${requestScope.ORDER.paymentStatus eq 0 or requestScope.ORDER.paymentStatus eq 1}">
                                        <c:url var="momo" value="reMomo">
                                            <c:param name="txtOrderID" value="${requestScope.ORDER.orderID}"></c:param>
                                        </c:url>
                                        <a href="${momo}" class="btn btn-success">Redirect to Momo</a>
                                    </c:if>
                                </c:if>
                            </div>
                            <h3 class="title">Details</h3>
                            <c:forEach var="detail" items="${requestScope.ORDER.detailList}" varStatus="counter">
                                <div class="item">
                                    <span class="price">${detail.total} VND</span>
                                    <p class="item-name">${detail.productName}</p>
                                    <p class="item-description">Quantity: ${detail.quantity}, Price ea.:${detail.total/detail.quantity} VND</p>
                                </div>
                            </c:forEach>
                            <div class="total">Total<span class="price">${requestScope.ORDER.total} VND</span></div>
                        </div>
                    </div>
                </section>
            </c:if>
            <c:if test="${not empty param.txtOrderID and empty requestScope.ORDER}">
                <h3 class="text-center text-muted mt-4 alert alert-danger mr-4 ml-4">The Order you enter cannot be found or not belong to you</h3>
            </c:if>
        </c:if>
    </body>
</html>
