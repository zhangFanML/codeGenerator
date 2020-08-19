<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragrma","no-cache");
    response.setDateHeader("Expires",0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Code Generator</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="static/js/lock/all.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="static/js/lock/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="static/js/lock/adminlte.min.css">
    <link rel="stylesheet" href="static/js/lock/font-awesome.min.css">
    <!-- Google Font: Source Sans Pro -->
    <link href="static/js/lock/cssfamily.css" rel="stylesheet">
</head>
<body class="hold-transition lockscreen">
<!-- Automatic element centering -->
<div class="lockscreen-wrapper">
    <div class="lockscreen-logo">
        <a href="/"><b>Code Generator</b></a>
    </div>
    <!-- User name -->
    <div class="lockscreen-name">Global InfoTech</div>

    <!-- START LOCK SCREEN ITEM -->
    <div class="lockscreen-item">
        <!-- lockscreen image -->
        <div class="lockscreen-image">
            <img src="static/images/128.ico" alt="User Image">
        </div>
        <!-- /.lockscreen-image -->

        <!-- lockscreen credentials (contains the form) -->
        <form action="/login" method="post" class="lockscreen-credentials">
            <div class="input-group">
                <input type="password" class="form-control" placeholder="password">

                <div class="input-group-append">
                    <button type="submit" class="btn"><i class="fas fa-arrow-right text-muted"></i></button>
                </div>
            </div>
        </form>
        <!-- /.lockscreen credentials -->

    </div>
    <!-- /.lockscreen-item -->
    <div class="help-block text-center">
        Enter your password to retrieve your session
    </div>
    <div class="lockscreen-footer text-center">
        Copyright &copy; 2014-2019 <b><a href="" class="text-black">AdminFan.io</a></b><br>
        All rights reserved
    </div>
</div>
<!-- /.center -->
<script>
    // js 版本
    // window.onload=function(){
    //     document.onkeydown=function(ev){
    //         var event=ev ||event
    //         if(event.key=='Enter'){
    //             alert("按了enter键")
    //         }
    //     }
    // }

</script>

<!-- jQuery -->
<script src="static/Jquery-3.2.1/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="static/js/lock/bootstrap.bundle.min.js"></script>
</body>
</html>
