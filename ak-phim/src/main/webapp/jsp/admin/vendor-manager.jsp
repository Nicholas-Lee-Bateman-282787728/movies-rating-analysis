<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" media="screen" href="/css/master.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/vendor-manager.css"/>
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">
    </head>

    <body>

        <c:set var="user" value="${sessionScope.USER}"/>

        <div class="wrapper">
            <div class="header">
                <div class="header-title">
                    <a href="/">
                        AK Phim
                    </a>
                </div>
                <div class="header-username">
                    ${user.lastName} ${user.middleName} ${user.firstName}
                </div>
                <div class="header-logout">
                    <a href="/dang-xuat" style="text-decoration: none; color: black">
                        Đăng xuất
                    </a>
                </div>
            </div>
            <div class="body">

                <div class="body-table">

                    <c:set var="vendors" value="${requestScope.VENDORS}"/>

                    <c:import charEncoding="UTF-8" url="/xsl/vendor-manager.xsl" var="xslDoc"/>
                    <x:transform doc="${vendors}" xslt="${xslDoc}"/>

                </div>


                <div class="body-adduser">
                    <div id="inform">

                    </div>
                    <form id="form-signup" class="adduser-form">
                        <input type="text" placeholder=" Tên tài khoản"
                               required
                               id="username"
                               name="username"/>
                        <input type="password" placeholder=" Mật khẩu"
                               required
                               id="password"
                               name="password"/>
                        <input type="password" placeholder=" Nhập lại mật khẩu"
                               required
                               id="re-password"
                               name="re-password"/>
                        <input type="text" placeholder=" Tên khách hàng"
                               required
                               id="name"
                               name="name"/>
                        <input type="text" placeholder=" Địa chỉ"
                               required
                               id="address1"
                               name="address1"/>
                        <input type="text" placeholder=" Địa chỉ bổ sung"
                               id="address2"
                               name="address2"/>
                        <input type="text" placeholder=" Số điện thoại"
                               required
                               id="tel"
                               name="tel"/>
                        <input type="text" placeholder=" Email"
                               required
                               id="email"
                               name="email"/>
                        <button type="button"
                                onclick="addVendor('form-signup');">
                            Thêm khách hàng
                        </button>
                    </form>
                </div>
            </div>

            <!-- Footer -->
            <c:import url="../fragments/footer.jsp"/>
        </div>

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

                                    var rows = document.getElementsByClassName('table-body-row').length;

                                    var tableBody = document.getElementById('table-vendor');
                                    var tRow = document.createElement('div');
                                    tRow.className = 'table-body-row';

                                    var countCell = document.createElement('div');
                                    countCell.className = 'table-body-item';
                                    countCell.innerHTML = rows + 1;
                                    tRow.appendChild(countCell);

                                    var nameCell = document.createElement('div');
                                    nameCell.className = 'table-body-item';
                                    var name = document.getElementById('name').value;
                                    nameCell.innerHTML = name;
                                    tRow.appendChild(nameCell);

                                    var telCell = document.createElement('div');
                                    telCell.className = 'table-body-item';
                                    var tel = document.getElementById('tel').value;
                                    telCell.innerHTML = tel;
                                    tRow.appendChild(telCell);

                                    var emailCell = document.createElement('div');
                                    emailCell.className = 'table-body-item';
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
