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