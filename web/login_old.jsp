<%-- 
    Document   : login
    Created on : 27/10/2011, 11:38:49 AM
    Author     : admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html class="no-js">
    <head>
        <link href="img/iammagis.png" rel="Shortcut Icon" /> 
        <title><bean:message key="lb.title"/></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="">
        <meta name="author" content="" />

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" />		
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />	
        <link rel="stylesheet" href="js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.css" type="text/css" />	

        <link rel="stylesheet" href="css/App.css" type="text/css" />
        <link rel="stylesheet" href="css/Login.css" type="text/css" />

        <link rel="stylesheet" href="css/custom.css" type="text/css" />

    </head>

    <body>

        <div id="login-container"> 
            <div id="login"> 
                <h3><bean:message key="lb.title"/></h3> 
                <h5><bean:message key="lb.subinicial"/></h5>  
                <form name="Usuario" action="LoginUser.appropiate" method="post">  
                    <div class="form-group" style="color: red;">
                        <html:errors property="register" />
                    </div>
                    <div class="form-group">
                        <label for="login-username"><bean:message key="lb.user"/></label>
                        <input type="text" name="correo" class="form-control" id="login-username" placeholder="<bean:message key="lb.user"/>" required>
                    </div>

                    <div class="form-group">
                        <label for="login-password"><bean:message key="lb.pass"/></label>
                        <input name="pass" type="password" class="form-control" id="login-password" placeholder="<bean:message key="lb.pass"/>" required>
                    </div>

                    <div class="form-group">

                        <button type="submit" id="login-btn" class="btn btn-primary btn-block"><bean:message key="lb.signin"/> &nbsp; <i class="fa fa-play-circle"></i></button>

                    </div>
                </form>


                <a href="forgot_password.lb" class="btn btn-default"><bean:message key="lb.forgoten"/></a>

            </div> <!-- /#login -->



        </div> <!-- /#login-container -->

        <script src="js/libs/jquery-1.9.1.min.js"></script>
        <script src="js/libs/jquery-ui-1.9.2.custom.min.js"></script>
        <script src="js/libs/bootstrap.min.js"></script> 
        <script src="js/App.js"></script> 
        <script src="js/forms/Login.js"></script>


    </body>
</html>
