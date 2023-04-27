<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f1f1f1;
    }
    h1 {
        color: #333;
        text-align: center;
    }
    form {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 20px;
    }
    input[type="submit"] {
        background-color: #6ab9d9;
        border: none;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        margin: 10px;
    }
    input[type="submit"]:hover {
        background-color: #6ab9d9;
    }
</style>
</head>
<body>
    <h1>Payment Successful</h1>
    <form method="post" action="products.htm">
        <input type="submit" value="viewOrders" name="userSelectedOption">
    </form>
    <form method="post" action="email.htm">
        <input type="submit" value="Email Orders" name="userSelectedOption">
    </form>
</body>
</html> 
