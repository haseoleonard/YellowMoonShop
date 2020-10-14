<%-- 
    Document   : viewcart
    Created on : Oct 8, 2020, 5:47:49 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons">
        <style>
            html *{
                -webkit-font-smoothing: antialiased;

            }
            body{
                background: #fff !important;
            }
            a {
                color: #3e3947 !important;
                text-decoration: none;
            }

            a:hover{
                color: #89229b !important;
                text-decoration: none !important;
            }
            a:focus {
                color: #89229b !important;
                text-decoration: none !important;
            }
            .container h3{
                font-size:25px ;
                margin-top: 20px ;
                margin-bottom: 10px ;
                font-weight: 300 ;
                color: #3c4858 ;
            }
            .container h4{
                font-size: 18px;
                line-height: 1.5;
                margin: 10px 0;
                font-weight: 300;
                color: #3c4858;
            }

            small{
                font-size: 75% !important;
                color: #777;
            }

            .btn-group{
                position: relative;
                margin: 10px 1px;
                display: inline-flex;
                vertical-align: middle;
            }
            .btn-group .btn{
                padding: 6.5px 20px !important;
            }
            .btn.btn-round{
                border-radius: 30px !important;
            }

            .btn-group .btn.btn-round{
                border-radius: 30px !important;
            }

            .btn-group>.btn:not(:last-child):not(.dropdown-toggle) {
                border-top-right-radius: 0 !important;
                border-bottom-right-radius: 0 !important;
            }

            .btn-group>.btn:not(:first-child) {
                border-top-left-radius: 0 !important;
                border-bottom-left-radius: 0 !important;
            }
            .btn{
                padding: 12px 30px !important;
                margin: 5px 1px;
                font-size: 12px !important;
                box-shadow: 0 2px 2px 0 hsla(0,0%,60%,.14), 0 3px 1px -2px hsla(0,0%,60%,.2), 0 1px 5px 0 hsla(0,0%,60%,.12);
            }
            .btn .material-icons{
                position: relative;
                display: inline-block;
                top: 0;
                margin-top: -1.2em;
                margin-bottom: -1em;
                font-size: 1.1rem;
                vertical-align: middle;
            }
            .btn.btn-sm{
                border-radius:3px !important;
            }

            .btn.btn-just-icon.btn-sm {
                height: 30px;
                min-width: 30px;
                width: 30px;
            }

            .btn.btn-just-icon {
                font-size: 24px;
                height: 41px;
                min-width: 41px;
                width: 41px;
                padding: 0 !important;
                overflow: hidden;
                position: relative;
                line-height: 41px;
            }

            .btn.btn-just-icon.btn-round {
                border-radius: 50% !important;
            }

            .btn.btn-link{
                background: transparent;
                box-shadow: none;
                color: #999;
            }

            .btn.btn-info {
                color: #fff !important;
                background-color: #00bcd4 !important;
                border-color: #00bcd4;
                box-shadow: 0 2px 2px 0 rgba(0,188,212,.14),
                    0 3px 1px -2px rgba(0,188,212,.2),
                    0 1px 5px 0 rgba(0,188,212,.12) !important;
            }

            .btn.btn-info:hover {
                box-shadow: 0 14px 26px -12px rgba(0,188,212,.42),
                    0 4px 23px 0 rgba(0,0,0,.12),
                    0 8px 10px -5px rgba(0,188,212,.2) !important;
                background: #00aec5 !important;
            }

            .btn.btn-info.btn-link{
                background-color: transparent !important;
                color: #00bcd4 !important;
                box-shadow:none !important;
            }
            .btn.btn-success {
                color: #fff !important;
                background-color: #4caf50 !important;
                border-color: #4caf50;
                box-shadow: 0 2px 2px 0 rgba(76,175,80,.14),
                    0 3px 1px -2px rgba(76,175,80,.2), 
                    0 1px 5px 0 rgba(76,175,80,.12) !important;
            }

            .btn.btn-success:hover {
                box-shadow: 0 14px 26px -12px rgba(76,175,80,.42), 
                    0 4px 23px 0 rgba(0,0,0,.12),   
                    0 8px 10px -5px rgba(76,175,80,.2) !important;
                background: #47a44b  !important;
            }
            .btn.btn-success.btn-link{
                background-color: transparent !important;
                color: #4caf50 !important;
                box-shadow: none !important;
            }

            .btn.btn-danger {
                color: #fff !important;
                background-color: #f44336 !important;
                border-color: #f44336;
                box-shadow: 0 2px 2px 0 rgba(244,67,54,.14), 
                    0 3px 1px -2px rgba(244,67,54,.2), 
                    0 1px 5px 0 rgba(244,67,54,.12) !important;
            }

            .btn.btn-danger:hover {
                box-shadow: 0 14px 26px -12px rgba(244,67,54,.42), 
                    0 4px 23px 0 rgba(0,0,0,.12), 
                    0 8px 10px -5px rgba(244,67,54,.2) !important;
                background-color: #f33527 !important;            

            }

            .btn.btn-danger.btn-link{
                background-color: transparent !important;
                color: #f44336 !important;
                box-shadow: none !important;
            }
            .btn.btn-just-icon .material-icons {
                margin-top: 0;
                position: absolute;
                width: 100%;
                transform: none;
                left: 0;
                top: 0;
                height: 100%;
                line-height: 41px;
                font-size: 20px;
            }

            .btn.btn-just-icon.btn-sm .material-icons {
                font-size: 17px; 
                line-height: 29px; 
            }


            .table {
                width: 100%;
                max-width: 100%;
                margin-bottom: 16px;
                background-color: transparent;
            }

            .table thead tr th {
                font-size: 17px ;
                font-weight: 300;
            }

            .table>thead>tr>th{
                padding: 12px 8px;
                vertical-align: middle;
                border-color: #ddd;
                font-weight: 300;
            }

            .table>tbody>tr>td{
                padding: 12px 8px;
                vertical-align: middle;
                border-color: #ddd;
                font-weight: 300;
                font-size:14px;
                color: #3c4858;
            }

            .table .td-actions .btn {
                margin: 0;
                padding: 5px;
            }

            .table .form-check {
                margin: 0;
                padding-left: 0;
            }

            .table .td-total {
                font-weight: 500;
                font-size: 17px;
                padding-top: 20px;
                text-align: right;
            }

            .table .td-price {
                font-size: 26px;
                font-weight: 300;
                margin-top: 5px;
                text-align: right;
            }
            .table-shopping>thead>tr>th {
                font-size: 12px;
                text-transform: uppercase;
                color: #555;
            }
            .table-shopping .td-name {
                min-width: 200px;
                font-weight: 400;
                font-size: 24px; 
                line-height: 1.42857143;
            }

            .table-shopping .td-name small {
                color: #999;
                font-size: 18px; 
                font-weight: 300;
            }

            .table-shopping .img-container {
                width: 120px;
                max-height: 160px;
                overflow: hidden;
                display: block;
            }

            .table-shopping .img-container img {
                width: 100%;
            }

            .table-shopping>tbody>tr>td {
                font-size: 14px;
            }

            .table-shopping .td-number {
                text-align: right;
                min-width: 150px;
                font-size: 18px;
            }

            .table-shopping .td-number small {
                margin-right: 3px;
            }
            .form-check{
                padding-left:0;
            }
            .form-check .form-check-label {
                cursor: pointer;
                padding-left: 0px;
                position: relative;
                margin-bottom: 0;
            }

            .form-check .form-check-label span {
                display: block;
                left: -1px;
                top: -1px;
                transition-duration: .2s;
            }

            .form-check .form-check-input {
                opacity: 0;
                height: 0;
                width: 0;
                overflow: hidden;
                position: absolute;
                margin: 0;
                z-index: -1;
                left: 0;
                pointer-events: none;
            }

            .form-check .form-check-sign:before {
                display: block;
                position: absolute;
                left: 0;
                content: "";
                background-color: rgba(0,0,0,.84);
                height: 20px;
                width: 20px;
                border-radius: 100%;
                z-index: 1;
                opacity: 0;
                margin: 0;
                top: 0;
                transform: scale3d(2.3,2.3,1);
            }

            .form-check .form-check-sign .check {
                position: relative;
                display: inline-block;
                width: 20px;
                height: 20px;
                border: 1px solid rgba(0,0,0,.54);
                overflow: hidden;
                z-index: 1;
                border-radius: 3px;
                top: 3px;
            }

            .form-check .form-check-sign .check:before {
                position: absolute;
                content: "";
                transform: rotate(45deg);
                display: block;
                margin-top: -3px;
                margin-left: 7px;
                width: 0;
                color: #fff;
                height: 0;
                box-shadow: 0 0 0 0, 0 0 0 0, 0 0 0 0, 0 0 0 0, 0 0 0 0, 0 0 0 0, inset 0 0 0 0;
                animation: checkbox-off .3s forwards;
            }

            .form-check .form-check-input:checked+.form-check-sign .check:before {
                color: #fff;
                box-shadow: 0 0 0 10px, 10px -10px 0 10px, 32px 0 0 20px, 0 32px 0 20px, -5px 5px 0 10px, 20px -12px 0 11px;
                animation: checkbox-on .3s forwards;
            }

            .form-check .form-check-input:checked+.form-check-sign .check {
                background: #9c27b0;
            }

            .table .form-check .form-check-sign {
                top: -13px;
                left: 0;
                padding-right: 0;
            }

            .table-striped>tbody>tr:nth-of-type(odd) {
                background-color: #f9f9f9 !important;
            }
            footer{
                width: 100%;
                margin-top:200px;
                color: #555;
                background: #fff;
                padding: 25px;
                font-weight: 300;
                display: block;
                position: absolute;
                float: left;
                vertical-align: middle;
            }


            .footer p{
                margin-bottom: 0;
            }
            footer p a{
                color: #555;
                font-weight: 400;
            }

            footer p a:hover{
                color: #9f26aa;
                text-decoration: none;
            }

            /*animation*/

            @keyframes checkbox-on {
                0% {
                    box-shadow:
                        0 0 0 10px,
                        10px -10px 0 10px,
                        32px 0 0 20px,
                        0px 32px 0 20px,
                        -5px 5px 0 10px,
                        15px 2px 0 11px;
                }
                50% {
                    box-shadow:
                        0 0 0 10px,
                        10px -10px 0 10px,
                        32px 0 0 20px,
                        0px 32px 0 20px,
                        -5px 5px 0 10px,
                        20px 2px 0 11px;
                }
                100% {
                    box-shadow:
                        0 0 0 10px,
                        10px -10px 0 10px,
                        32px 0 0 20px,
                        0px 32px 0 20px,
                        -5px 5px 0 10px,
                        20px -12px 0 11px;
                }
            }

            .tooltip-arrow {
                display: none;
            }

            .tooltip.show {
                display: block;
                opacity: 1;
                -webkit-transform: translate3d(0, 0px, 0);
                -moz-transform: translate3d(0, 0px, 0);
                -o-transform: translate3d(0, 0px, 0);
                -ms-transform: translate3d(0, 0px, 0);
                transform: translate3d(0, 0px, 0);
            }

            .tooltip {
                opacity: 0;
                transition: opacity, transform .2s ease;
                -webkit-transform: translate3d(0, 5px, 0);
                -moz-transform: translate3d(0, 5px, 0);
                -o-transform: translate3d(0, 5px, 0);
                -ms-transform: translate3d(0, 5px, 0);
                transform: translate3d(0, 5px, 0);
                font-size: 0.75rem;
            }

            .tooltip.bs-tooltip-top .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="top"] .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="top"] .arrow::before {
                border-top-color: #fff;
            }

            .tooltip.bs-tooltip-right .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="right"] .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="right"] .arrow::before {
                border-right-color: #fff;
            }

            .tooltip.bs-tooltip-left .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="left"] .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="left"] .arrow::before {
                border-left-color: #fff;
            }

            .tooltip.bs-tooltip-bottom .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="bottom"] .arrow::before, .tooltip.bs-tooltip-auto[x-placement^="bottom"] .arrow::before {
                border-bottom-color: #fff;
            }

            .tooltip-inner {
                padding: 10px 15px !important;
                min-width: 130px;
            }

            .tooltip-inner {
                line-height: 1.5em;
                background: #fff !important;
                border: none;
                border-radius: 3px;
                box-shadow: 0 8px 10px 1px rgba(0, 0, 0, 0.14), 0 3px 14px 2px rgba(0, 0, 0, 0.12), 0 5px 5px -3px rgba(0, 0, 0, 0.2);
                color: #555 !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
            <h1 class="text-center mb-4 mt-4">Your Cart</h1>
        <c:if test="${not empty sessionScope.CART.items}">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-md-12 ml-auto mr-auto">
                        <h4><small>List of Product In Cart</small></h4>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th class="text-center">#</th>
                                        <th>Product Name</th>
                                        <th>Price ea</th>
                                        <th>Quantity</th>
                                        <th class="text-right">Total(VND)</th>
                                        <th class="text-right">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${sessionScope.CART.items}" varStatus="counter">
                                    <form id="updateForm${counter.count}" action="updateCart" method="POST">
                                        <input type="hidden" name="txtProductID" value="${item.key}"/>
                                    </form>
                                    <form id="removeForm${counter.count}" action="removeFromCart" method="POST">
                                        <input type="hidden" name="txtProductID" value="${item.key}"/>
                                    </form>
                                    <tr>
                                        <td class="text-center">
                                            ${counter.count}
                                        </td>
                                        <td>${item.value.itemName}</td>
                                        <td>${item.value.price} VND</td>
                                        <td>
                                            <input type="number" form="updateForm${counter.count}" required="true" min="1" max="${Integer.MAX_VALUE}" 
                                                   name="txtQuantity" value="${item.value.quantity}"/><br/>
                                            <c:if test="${requestScope.ERROR.productID eq item.key}">
                                                <c:if test="${not empty requestScope.ERROR.outOfStockErr}">
                                                    <font style="color: red">
                                                    ${requestScope.ERROR.outOfStockErr}<br/>
                                                    Only ${requestScope.ERROR.quantityLeft} products left.
                                                    </font>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td class="text-right">${item.value.total()} VND</td>
                                        <td class="td-actions text-right">
                                            <button type="submit" form="updateForm${counter.count}" rel="tooltip" class="btn btn-success btn-just-icon btn-sm" 
                                                    data-original-title="Update" form="updateForm" title="Update" name="btAction" value="Update">
                                                <i class="material-icons">edit</i>
                                            </button>
                                            <button type="submit" form="removeForm${counter.count}" onclick="return confirm('Are you sure to remove this product ?')" 
                                                    rel="tooltip" class="btn btn-danger btn-just-icon btn-sm" data-original-title="Remove" title="Remove">
                                                <i class="material-icons">close</i>
                                            </button>

                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr class="text-right">
                                    <td colspan="4"></td>
                                    <td><strong>${sessionScope.CART.total()} VND</strong></td>
                                    <td class="text-center">
                                        <a href="checkout.jsp" style="text-decoration: none">
                                            <div class="btn btn-warning btn-block" style="text-decoration: none; font-weight: bold; color: black;  background-color: #ffa64d">
                                                Check Out
                                            </div>
                                        </a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty sessionScope.CART.items}">
                    <h3 class="text-center text-muted">Your Cart is empty</h3>
                </c:if>
                <script>
                    $(document).ready(function () {
                        $('body').bootstrapMaterialDesign();
                        $('[data-toggle="tooltip"], [rel="tooltip"]').tooltip();

                    });
                </script>
                </body>
                </html>
