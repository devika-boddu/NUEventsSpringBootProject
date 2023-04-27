<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                background-color: #f2f2f2;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            input[type="text"], input[type="password"] {
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                width: 100%;
                box-sizing: border-box;
                margin-bottom: 20px;
            }
            input[type="submit"] {
                background-color: #1e1f1e;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #3e8e41;
            }
            .card{
                width: 100%;
            }
        </style>

    </head>
    <body>
        <h1> Login Page</h1>
        <p>Welcome</p>
        <div class="card">
            <form method="post" action = "adminDashboard.htm">
                <label>User Name:</label>
                <input type = "text" name = "adminName"/><br/>
                <label>Password:</label>
                <input type = "password" name = "adminPassword"/><br/>
                <input type = "submit" value = "Login"/> 
            </form>
        </div>
        
    </body>
</html>
