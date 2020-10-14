<%-- 
    Document   : login.jsp
    Created on : Oct 5, 2020, 9:05:16 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="${applicationScope.GOOGLE_CLIENT_ID}">
        <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
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
    <body class="bg-gradient-primary">
        <jsp:include page="WEB-INF/jspf/header.jsp"></jsp:include>
            <div class="container-fluid">
                <div class="row no-gutter">
                    <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
                    <div class="col-md-8 col-lg-6">
                        <div class="login d-flex align-items-center py-5">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-9 col-lg-8 mx-auto">
                                        <h3 class="login-heading mb-4">Welcome back!</h3>
                                        <form action="login" method="POST">
                                            <div class="form-label-group">
                                                <input type="text" id="inputEmail" class="form-control"
                                                       placeholder="Username" name="txtUsername" value="${param.txtUsername}" 
                                                maxlength="70" required autofocus>
                                            <label for="inputEmail">Username</label>
                                        </div>
                                        <div class="form-label-group">
                                            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtPassword" maxlength="50" required>
                                            <label for="inputPassword">Password</label>
                                        </div>
                                        <c:if test="${not empty requestScope.LOGIN_ERROR}">
                                            <font style="color: red">${requestScope.LOGIN_ERROR}</font>
                                        </c:if>
                                        <button class="btn btn-lg btn-warning btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit">Sign in</button>
                                        <hr class="my-4">
                                        <div class="d-flex justify-content-between">
                                            <div id="my-signin2" class="mr-auto ml-auto"></div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function onSuccess(googleUser) {
                var id_token = googleUser.getAuthResponse().id_token;
                var form = document.createElement("form");
                form.method = "POST";
                form.action = "loginGoogle"
                //
                var input1 = document.createElement("input");
                input1.type = "hidden";
                input1.name = "id_token";
                input1.value = id_token;
                form.appendChild(input1);
                //
                document.body.appendChild(form);
                googleUser.disconnect();
                form.submit();
            }
            function onFailure(error) {
                console.log(error);
            }
            function renderButton() {
                gapi.signin2.render('my-signin2', {
                    'scope': 'profile email',
                    'width': 300,
                    'height': 50,
                    'longtitle': true,
                    'theme': 'dark',
                    'onsuccess': onSuccess,
                    'onfailure': onFailure
                });
            }
        </script>
        <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
    </body>
</html>
