<%--
  Created by IntelliJ IDEA.
  User: anh-khue
  Date: 04/06/18
  Time: 08:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>
Hello world!
<table id="show-xml" border="1">
    <thead>
    <tr>
        <th>No.</th>
        <th>Username</th>
        <th>Full name</th>
    </tr>
    </thead>
    <tbody id="xml-content">

    </tbody>
</table>

<script type="application/javascript">
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            var tbody = document.getElementById("xml-content");

            var parser = new DOMParser();
            console.log(xmlHttpRequest.responseText);
            var xmlDoc = parser.parseFromString(xmlHttpRequest.responseText, "text/xml");
            var xpathExp = "//account";
            var accounts = xmlDoc.evaluate(xpathExp, xmlDoc, null, XPathResult.ANY_TYPE, null);

            var account = accounts.iterateNext();

            var i = 0;
            while (account) {
                var row = document.createElement('tr');
                var indexCell = document.createElement('td');
                indexCell.innerText = i + 1;
                row.appendChild(indexCell);

                var username = xmlDoc.evaluate('username', account, null, XPathResult.STRING_TYPE, null).stringValue;
                // console.log(username);
                var usernameCell = document.createElement('td');
                usernameCell.innerText = username;
                row.appendChild(usernameCell);

                var fullName = xmlDoc.evaluate('lastName', account, null, XPathResult.STRING_TYPE, null).stringValue
                    + " " + xmlDoc.evaluate('middleName', account, null, XPathResult.STRING_TYPE, null).stringValue
                    + " " + xmlDoc.evaluate('firstName', account, null, XPathResult.STRING_TYPE, null).stringValue;
                var fullNameCell = document.createElement('td');
                fullNameCell.innerText = fullName;
                row.appendChild(fullNameCell);

                tbody.appendChild(row);
                account = accounts.iterateNext();
            }
        }
    };

    xmlHttpRequest.open('GET', '/accounts/test', false);
    xmlHttpRequest.send();
</script>
</body>
</html>
