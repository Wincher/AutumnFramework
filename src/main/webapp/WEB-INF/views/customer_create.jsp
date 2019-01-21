<%--
  Created by IntelliJ IDEA.
  User: wincher
  Date: 2018/8/23
  Time: 02:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Customer - Create</title>
</head>
<body>
<h1>create customer view</h1>

<form id="customer_form" enctype="multipart/form-data">
    <table>
        <tr>
            <td>customer name:</td>
            <td>
                <input type="text" name="name" value="${customer.name}"/>
            </td>
        </tr>
        <tr>
            <td>contact:</td>
            <td>
                <input type="text" name="contact" value="${customer.contact}"/>
            </td>
        </tr>
        <tr>
            <td>tel:</td>
            <td>
                <input type="text" name="tel" value="${customer.tel}"/>
            </td>
        </tr>
        <tr>
            <td>email:</td>
            <td>
                <input type="text" name="email" value="${customer.email}"/>
            </td>
        </tr>
        <tr>
            <td>photo:</td>
            <td>
                <input type="file" name="photo" value="${customer.photo}"/>
            </td>
        </tr>
    </table>
    <button type="submit">save</button>
</form>

<script src="${BASE}/asset/lib/jquery/jquery.min.js"></script>
<script src="${BASE}/asset/lib/jquery-form/jquery.form.min.js"></script>
<script>
    $(function() {
        $('#customer_form').ajaxForm({
            type: 'post',
            url: '${BASE}/customer_create',
            success: function(data) {
                if (data) {
                    location.href = '${BASE}/customer';
                } else {
                    alert('shit happens');
                }
            }
        });
    });
</script>
</body>
</html>
