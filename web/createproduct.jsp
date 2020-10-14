<%-- 
    Document   : createproduct
    Created on : Oct 7, 2020, 12:24:36 PM
    Author     : haseo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/jspf/header.jsp" flush="true"></jsp:include>
            <h1 class="text-center text-muted pt-3 pb-3">Create Product</h1>
            <form action="createCategory" id="createCategory" method="POST"></form>
            <div class="container">
                <div class="col-md-6 mx-auto border border-secondary bg-light pt-4 pb-4 pl-4 pr-4">
                    <form action="create" method="post" enctype="multipart/form-data">
                        <div class="form-group mb-0">
                            <label>Product Name</label>
                            <input class="form-control" type="text" name="txtProductName" value="${param.txtProductName}" required="true" autofocus="true"/><br/>
                        <c:if test="${not empty requestScope.ERROR.emptyProductName}">
                            <font style="color: red">${requestScope.ERROR.emptyProductName}</font><br/>
                        </c:if>
                    </div>
                    <div class="form-group mb-0">
                        <label>Description</label>
                        <input class="form-control" type="text" name="txtDescription" value="${param.txtDescription}" required/><br/>
                        <c:if test="${not empty requestScope.ERROR.emptyDescription}">
                            <font style="color: red">${requestScope.ERROR.emptyDescription}</font><br/>
                        </c:if>
                    </div>
                    <div class="row mb-0">
                        <div class="col-md-6 form-group mb-0">
                            <label>Create Date</label>
                            <input class="form-control" type="datetime-local"name="txtCreateDate" min="2018-06-07T00:00" step="1"
                                   value="${requestScope.CURRENT_DATETIME}" required="true"/><br/>
                            <c:if test="${not empty requestScope.ERROR.invalidCreateDate}">
                                <font style="color: red">${requestScope.ERROR.invalidCreateDate}</font><br/>
                            </c:if>
                        </div>                            
                        <div class="col-md-6 form-group mb-0">
                            <label>Expiration Date</label>
                            <input class="form-control" type="date" name="txtExpirationDate" min="${requestScope.CURRENT_DATE}" 
                                   value="${requestScope.CURRENT_DATE}" name="txtExpirationDate" required="true"/><br/>
                            <c:if test="${not empty requestScope.ERROR.invalidExpirationDate}">
                                <font style="color: red">${requestScope.ERROR.invalidExpirationDate}</font><br/>
                            </c:if>
                        </div>
                    </div>                        
                    <div class="row">
                        <div class="col-md-6 form-group mb-0">
                            <label>Quantity</label>
                            <input class="form-control" type="number" name="txtQuantity" max="${Integer.MAX_VALUE}" min="0"
                                   <c:if test="${empty param.txtQuantity}">value="0"</c:if>
                                   <c:if test="${not empty param.txtQuantity}">value="${param.txtQuantity}"</c:if>/><br/>
                            <c:if test="${not empty requestScope.ERROR.invalidQuantity}">
                                <font style="color: red">${requestScope.ERROR.invalidQuantity}</font><br/>
                            </c:if>    
                        </div>                                                  
                        <div class="col-md-6 form-group mb-0">
                            <label>Price(VND)</label>
                            <input class="form-control" type="number" name="txtPrice" step="1000" max="${Integer.MAX_VALUE}" min="0"
                                   <c:if test="${empty param.txtPrice}">value="0"</c:if>
                                   <c:if test="${not empty param.txtPrice}">value="${param.txtPrice}"</c:if> />
                            <c:if test="${not empty requestScope.ERROR.invalidPrice}">
                                <font style="color: red">${requestScope.ERROR.invalidPrice}</font><br/>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label>Category</label>
                            <select class="form-control" name="txtCategory">
                                <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                                    <option 
                                        <c:if test="${requestScope.LOADED_PRODUCT.categoryID eq category.categoryID}">
                                            selected="true"
                                        </c:if>>
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty requestScope.ERROR.emptyCategory}">
                                <font style="color: red">${requestScope.ERROR.emptyCategory}</font><br/>
                            </c:if>
                        </div>
                        <div class="form-group mb-0">
                            <label>New Category Name</label>
                            <input type="text" name="txtCategoryName" form="createCategory" class="form-control"
                                   value="${param.txtCategoryName}" required="true" maxlength="150"/><br/>
                            <input type="submit" form="createCategory" name="btAction" class="btn btn-warning" value="Create Category"/>
                        </div>
                    </div>
                    <label>Image</label>
                    <div class="input-group mt-2">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Upload</span>
                        </div>
                        <div class="custom-file">
                            <input type="file" name="image" accept="image/gif,image/jpeg,image/png" class="custom-file-input" id="inputGroupFile01">
                            <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                        </div>
                        <c:if test="${not empty requestScope.ERROR.invalidFileType}">
                            <font style="color: red">${requestScope.ERROR.invalidFileType}</font><br/>
                        </c:if>
                    </div>
                    <div class="justify-content-between text-center pt-2 img-container img-thumbnail">
                        <img id="img" class="img-fluid" src="" alt="">
                    </div>
                    <div class="row pt-3">
                        <input class="col-md-6 btn btn-warning btn-block mx-auto" type="submit" name="btAction" value="Create"/>
                    </div>
                </form>
            </div>
        </div>        
        <script>
            $('#inputGroupFile01').on('change', function () {
                //get the file name
                var fileName = $(this).val();
                fileName = fileName.replace('C:\\fakepath\\', "");
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);
                readURL(this);
            });
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('img').attr('src', e.target.result);
                    };

                    reader.readAsDataURL(input.files[0]);
                }
            }
        </script>
    </body>
</html>
