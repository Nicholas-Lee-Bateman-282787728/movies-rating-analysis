function authenticate() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var request = new XMLHttpRequest();
    var url = '/sign-in';
    var params = 'username=' + username + '&password=' + password;
    request.open('POST', url, true);

    // Send the proper header information along with the request
    request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    request.onreadystatechange = function () {

        if (request.readyState === 4 && request.status === 200) {
            window.location.replace("http://localhost:8080/");
        } else if (request.status === 400) {
            var errorDiv = document.getElementById('error');
            errorDiv.innerHTML = "";

            var errorMessage = document.createElement('div');
            errorMessage.classList.add('alert', 'alert-danger');
            errorMessage.innerHTML = request.responseText;

            errorDiv.appendChild(errorMessage);
        }
    };
    request.send(params);
}

function signUp(formId) {
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
            var request = new XMLHttpRequest();
            var url = '/sign-up';

            request.open('POST', url);
            request.onreadystatechange = function () {
                if (request.readyState === 4 && request.status === 201) {
                    var informMessage = document.createElement('div');
                    informMessage.classList.add('alert', 'alert-success');
                    informMessage.innerHTML = "Tài khoản đã được tạo thành công.<br/>Chuẩn bị điều hướng để Đăng nhập";
                    informDiv.appendChild(informMessage);

                    window.setTimeout(function () {
                        window.location.replace("http://localhost:8080/dang-nhap");
                    }, 3000);
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
            request.send(form);
        }
    }
}