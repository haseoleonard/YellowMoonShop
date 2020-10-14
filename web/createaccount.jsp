<%-- 
    Document   : createaccount.jsp
    Created on : Oct 5, 2020, 9:26:09 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
        <style>
            :root {
                --input-padding-x: 1.5rem;
                --input-padding-y: 0.75rem;
            }

            .login,
            .image {
                min-height: 100vh;
            }

            .bg-image {
                background-image: url('https://cdn.asiatatler.com/asiatatler/i/hk/2018/11/06175032-story-image-52163_cover_2000x1683.jpg');
                background-size: cover;
                background-position: center;
            }

            .login-heading {
                font-weight: 300;
            }

            .btn-login {
                font-size: 0.9rem;
                letter-spacing: 0.05rem;
                padding: 0.75rem 1rem;
                border-radius: 2rem;
            }

            .form-label-group {
                position: relative;
                margin-bottom: 1rem;
            }

            .form-label-group>input,
            .form-label-group>label {
                padding: var(--input-padding-y) var(--input-padding-x);
                height: auto;
                border-radius: 2rem;
            }

            .form-label-group>label {
                position: absolute;
                top: 0;
                left: 0;
                display: block;
                width: 100%;
                margin-bottom: 0;
                /* Override default `<label>` margin */
                line-height: 1.5;
                color: #495057;
                cursor: text;
                /* Match the input under the label */
                border: 1px solid transparent;
                border-radius: .25rem;
                transition: all .1s ease-in-out;
            }

            .form-label-group input::-webkit-input-placeholder {
                color: transparent;
            }

            .form-label-group input:-ms-input-placeholder {
                color: transparent;
            }

            .form-label-group input::-ms-input-placeholder {
                color: transparent;
            }

            .form-label-group input::-moz-placeholder {
                color: transparent;
            }

            .form-label-group input::placeholder {
                color: transparent;
            }

            .form-label-group input:not(:placeholder-shown) {
                padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
                padding-bottom: calc(var(--input-padding-y) / 3);
            }

            .form-label-group input:not(:placeholder-shown)~label {
                padding-top: calc(var(--input-padding-y) / 3);
                padding-bottom: calc(var(--input-padding-y) / 3);
                font-size: 12px;
                color: #777;
            }

            /* Fallback for Edge
            -------------------------------------------------- */

            @supports (-ms-ime-align: auto) {
                .form-label-group>label {
                    display: none;
                }
                .form-label-group input::-ms-input-placeholder {
                    color: #777;
                }
            }

            /* Fallback for IE
            -------------------------------------------------- */

            @media all and (-ms-high-contrast: none),
            (-ms-high-contrast: active) {
                .form-label-group>label {
                    display: none;
                }
                .form-label-group input:-ms-input-placeholder {
                    color: #777;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
            <div class="container-fluid">
                <div class="row no-gutter">
                    <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
                    <div class="col-md-8 col-lg-6">
                        <div class="login d-flex align-items-center py-5">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-9 col-lg-8 mx-auto">
                                    <c:if test="${not empty sessionScope.NAME and not empty sessionScope.GOOGLE_ID and empty sessionScope.LOGIN_USER}">
                                        <p class="alert alert-warning">It seems like you don't have an account yet</p>
                                    </c:if>
                                    <h3 class="login-heading mb-4">Create New Account</h3>
                                    <form action="createAccount" method="POST">
                                        <div class="form-label-group">
                                            <input type="text" id="inputUsername" class="form-control" placeholder="Username" name="txtUsername" value="${param.txtUsername}" maxlength="50" required autofocus>
                                            <label for="inputUsername">Username</label>
                                        </div>
                                        <c:if test="${not empty requestScope.ERROR.usernameExistedErr}">
                                            <font style="color: red">${requestScope.ERROR.usernameExistedErr}</font>
                                        </c:if>
                                        <c:if test="${not empty requestScope.ERROR.usernameLengthErr}">
                                            <font style="color: red">${requestScope.ERROR.usernameLengthErr}</font>
                                        </c:if>
                                        <div class="form-label-group">
                                            <input type="text" id="inputName" class="form-control" placeholder="Name" name="txtName"
                                                   <c:if test="${not empty param.txtName}">value="${param.txtName}"</c:if>
                                                   <c:if test="${empty param.txtName and not empty sessionScope.NAME}">value="${sessionScope.NAME}"</c:if>
                                                       maxlength="200" required>
                                                   <label for="inputName">Name</label>
                                            </div>
                                        <c:if test="${not empty requestScope.ERROR.nameLengthErr}">
                                            <font style="color: red">${requestScope.ERROR.nameLengthErr}</font>
                                        </c:if>
                                        <div class="form-label-group">
                                            <input type="text" id="inputAddress" class="form-control" placeholder="Address" name="txtAddress" value="${param.txtAddress}" maxlength="400" required>
                                            <label for="inputAddress">Address</label>
                                        </div>
                                        <c:if test="${not empty requestScope.ERROR.addressLengthErr}">
                                            <font style="color: red">${requestScope.ERROR.addressLengthErr}</font>
                                        </c:if>
                                        <div class="form-label-group">
                                            <input type="text" id="inputPhone" class="form-control" placeholder="Phone Number" name="txtPhone" value="${param.txtPhone}" maxlength="12" required>
                                            <label for="inputPhone">Phone Number</label>
                                        </div>
                                        <c:if test="${not empty requestScope.ERROR.invalidPhoneErr}">
                                            <font style="color: red">${requestScope.ERROR.invalidPhoneErr}</font>
                                        </c:if>
                                        <div class="form-label-group">
                                            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtPassword" maxlength="64" required>
                                            <label for="inputPassword">Password</label>
                                        </div>
                                        <c:if test="${not empty requestScope.ERROR.passwordLengthErr}">
                                            <font style="color: red">${requestScope.ERROR.passwordLengthErr}</font>
                                        </c:if>
                                        <div class="form-label-group">
                                            <input type="password" id="inputConfirm" class="form-control" placeholder="Confirm" name="txtConfirm" maxlength="64" required>
                                            <label for="inputConfirm">Confirm</label>
                                        </div>
                                        <c:if test="${not empty requestScope.ERROR.confirmNotMatchedErr}">
                                            <font style="color: red">${requestScope.ERROR.confirmNotMatchedErr}</font>
                                        </c:if>
                                        <button class="btn btn-lg btn-warning btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit">Create New Account</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
