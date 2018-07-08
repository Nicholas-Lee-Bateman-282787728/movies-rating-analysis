<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">

        <!-- Bootstrap core CSS -->
        <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
              rel="stylesheet" type="text/css">

        <!-- Custom styles for this template -->
        <link href="/css/master.css" rel="stylesheet">
        <link href="/css/phim-moi.css" rel="stylesheet">
        <link href="/css/vendor-manager.css" rel="stylesheet">

    </head>

    <body>

        <!-- Header -->
        <jsp:include page="../fragments/header.jsp"/>

        <c:set var="currentUser" value="${sessionScope.USER}"/>

        <div class="container text-center sorry-message">
            <c:if test="${currentUser.role == 0}">

                <c:set var="vendors" value="${requestScope.VENDORS}"/>

                <div class="row">
                    <div class="col-md-6">
                        <c:import charEncoding="UTF-8" url="/xsl/vendor-manager.xsl" var="xslDoc"/>
                        <x:transform doc="${vendors}" xslt="${xslDoc}"/>
                    </div>

                    <div class="col-md-6">
                        <form id="form-signup" class="form-signin">
                            <div id="inform">

                            </div>
                            <label for="username" class="sr-only">Username</label>
                            <input type="text" id="username" name="username" class="form-control"
                                   placeholder="Tên tài khoản" required autofocus>
                            <label for="password" class="sr-only">Password</label>
                            <input type="password" id="password" name="password" class="form-control"
                                   placeholder="Mật khẩu"
                                   required>
                            <label for="re-password" class="sr-only">Re-enter Password</label>
                            <input type="password" id="re-password" class="form-control" placeholder="Nhập lại mật khẩu"
                                   required>
                            <label for="name" class="sr-only">Tên khách hàng</label>
                            <input type="text" id="name" name="name" class="form-control" placeholder="Tên khách hàng"
                                   required autofocus>
                            <label for="address1" class="sr-only">Địa chỉ</label>
                            <input type="text" id="address1" name="address1" class="form-control" placeholder="Địa chỉ"
                                   required autofocus>
                            <label for="address2" class="sr-only">Địa chỉ bổ sung</label>
                            <input type="text" id="address2" name="address2" class="form-control"
                                   placeholder="Địa chỉ bổ sung" autofocus>
                            <label for="tel" class="sr-only">Số điện thoại</label>
                            <input type="text" id="tel" name="tel" class="form-control" placeholder="Số điện thoại"
                                   required
                                   autofocus>
                            <label for="email" class="sr-only">Email</label>
                            <input type="text" id="email" name="email" class="form-control" placeholder="Email" required
                                   autofocus>
                            <button class="btn btn-lg btn-success btn-block" type="button"
                                    onclick="addVendor('form-signup');">
                                Thêm khách hàng
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>

            <c:if test="${currentUser.role != 0}">
                <h3 id="message">Xin lỗi!<br/>Bạn không có quyền truy cập trang này</h3>
            </c:if>
        </div>

        <!-- Footer -->
        <jsp:include page="../fragments/footer.jsp"/>

        <script type="text/javascript">
            function isValidEmail(email) {
                return /^[a-zA-Z][a-zA-Z0-9]*([.][a-zA-Z][a-zA-Z0-9]*)?[@][a-zA-Z]+[.][a-zA-Z]+([.][a-zA-Z]+)?$/.test(email);
            }

            function addVendor(formId) {
                var informDiv = document.getElementById('inform');
                informDiv.innerHTML = "";

                var form = new FormData(document.getElementById(formId));

                var filled = true;
                var requiredFields = document.querySelectorAll("[required]");
                for (var i = 0; i < requiredFields.length; i++) {
                    if (requiredFields[i].value === '') {
                        requiredFields[i].focus();
                        filled = false;
                        break;
                    }
                }

                if (filled) {
                    var rePassword = document.getElementById('re-password');
                    if (form.get('password') !== rePassword.value) {
                        var errorMessage = document.createElement('div');
                        errorMessage.classList.add('alert', 'alert-danger');
                        errorMessage.innerHTML = "Mật khẩu không trùng khớp.<br/>Vui lòng nhập lại";
                        informDiv.appendChild(errorMessage);
                        rePassword.value.innerHTML = "";

                        rePassword.focus();
                    } else {
                        var email = document.getElementById('email').value;

                        if (!isValidEmail(email)) {
                            errorMessage = document.createElement('div');
                            errorMessage.classList.add('alert', 'alert-danger');
                            errorMessage.innerHTML = "Email không phù hợp,<br/>vui lòng kiểm tra lại!";
                            informDiv.appendChild(errorMessage);
                            rePassword.value.innerHTML = "";
                        } else {
                            var request = new XMLHttpRequest();
                            var url = '/vendor';

                            request.open('POST', url);
                            request.onreadystatechange = function () {
                                if (request.readyState === 4 && request.status === 201) {
                                    var informMessage = document.createElement('div');
                                    informMessage.classList.add('alert', 'alert-success');
                                    informMessage.innerHTML = "Tài khoản đã được tạo thành công";
                                    informDiv.appendChild(informMessage);

                                    var rows = document.getElementById('table-vendor')
                                        .getElementsByTagName("tbody")[0]
                                        .getElementsByTagName("tr").length;

                                    var tableBody = document.getElementById('vendor-table-body');
                                    var tRow = document.createElement('tr');

                                    var countCell = document.createElement('th');
                                    countCell.setAttribute('scope', 'row');
                                    countCell.innerHTML = rows + 1;
                                    tRow.appendChild(countCell);

                                    var nameCell = document.createElement('td');
                                    var name = document.getElementById('name').value;
                                    nameCell.innerHTML = name;
                                    tRow.appendChild(nameCell);

                                    var telCell = document.createElement('td');
                                    var tel = document.getElementById('tel').value;
                                    telCell.innerHTML = tel;
                                    tRow.appendChild(telCell);

                                    var emailCell = document.createElement('td');
                                    var email = document.getElementById('email').value;
                                    emailCell.innerHTML = email;
                                    tRow.appendChild(emailCell);

                                    tableBody.appendChild(tRow);
                                    form.reset();
                                } else if (request.status === 406) {
                                    informDiv.innerHTML = "";
                                    informMessage = document.createElement('div');
                                    informMessage.classList.add('alert', 'alert-info');
                                    informMessage.innerHTML = request.responseText;
                                    informDiv.appendChild(informMessage);

                                    var username = document.getElementById('username');
                                    username.focus();
                                } else if (request.status === 500) {
                                    window.location.replace("http://localhost:8080/error");
                                }
                            };
                        }
                        request.send(form);
                    }
                }
            }
        </script>

    </body>
</html>