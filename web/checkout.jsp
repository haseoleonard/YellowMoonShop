<%-- 
    Document   : checkout
    Created on : Oct 9, 2020, 12:16:31 AM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out</title>
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
        <c:if test="${not empty sessionScope.CART}">
            <section class="payment-form dark pt-4">
                <div class="container">
                    <form action="checkout" method="POST">
                        <div class="products">
                            <h3 class="title">Checkout</h3>
                            <c:forEach var="item" items="${sessionScope.CART.items}" varStatus="counter">
                                <div class="item">
                                    <span class="price">${item.value.total()} VND</span>
                                    <p class="item-name">${item.value.itemName}</p>
                                    <p class="item-description">Quantity: ${item.value.quantity}, Price ea.:${item.value.price} VND</p>
                                </div>
                            </c:forEach>
                            <div class="total">Total<span class="price">${sessionScope.CART.total()} VND</span></div>
                        </div>                    
                        <div class="card-details">
                            <h3 class="title">Customer Details</h3>
                            <div class="row">
                                <div class="form-group col-sm-7">
                                    <label for="card-holder">Customer Name</label>
                                    <input id="card-holder" type="text" name="txtName" 
                                           required="true" maxlength="200" value="${sessionScope.LOGIN_USER.name}" 
                                           class="form-control" placeholder="Customer Name" aria-label="Customer Name" aria-describedby="basic-addon1">
                                    <c:if test="${not empty requestScope.ERRORS.emptyNameErr}">
                                        <font style="color: red">${requestScope.ERRORS.emptyNameErr}</font>
                                    </c:if>
                                </div>
                                <div class="form-group col-sm-5">
                                    <label for="phone-number">Phone Number</label>
                                    <div class="input-group expiration-date">
                                        <input type="text" name="txtPhone" required="true" id="phone-number" 
                                               maxlength="12" value="${sessionScope.LOGIN_USER.phone}" class="form-control"
                                               placeholder="Phone Number" aria-label="Phone Number" aria-describedby="basic-addon1"/>
                                        <c:if test="${not empty requestScope.ERRORS.invalidPhoneNumErr}">
                                            <font style="color: red">${requestScope.ERRORS.invalidPhoneNumErr}</font>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group col-sm-8">
                                    <label for="card-number">Customer Address</label>
                                    <input name="txtAddress" required="true" maxlength="200" value="${sessionScope.LOGIN_USER.address}"
                                           id="card-number" type="text" class="form-control" placeholder="Customer Address"
                                           aria-label="Customer Address" aria-describedby="basic-addon1"/>
                                    <c:if test="${not empty requestScope.ERRORS.emptyAddressErr}">
                                        <font style="color: red">${requestScope.ERRORS.emptyAddressErr}</font>
                                    </c:if>
                                </div>
                                <div class="form-group col-sm-4 ">
                                    <label for="paymentMethod">Payment Method</label>
                                    <div id="paymentMethod" class="justify-content-between ">
                                        <c:forEach var="payment" items="${requestScope.PAYMENT_LIST}">
                                            <input type="radio" id="method" name="txtPaymentMethod" value="${payment.methodType}"/>
                                            <label for="method">${payment.methodName}</label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <button type="submit" name="btAction" value="Check Out" class="btn btn-warning btn-block">Proceed</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </c:if>
        <c:if test="${empty sessionScope.CART}">
            <c:redirect url="productlist.jsp"></c:redirect>
        </c:if>
    </body>
</html>
