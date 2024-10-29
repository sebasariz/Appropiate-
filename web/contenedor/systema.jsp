
<%-- 
    Document   : contentRoot
    Created on : 13/03/2015, 02:45:57 PM
    Author     : sebasariz
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    if (request.getSession().getAttribute("usuario") == null) {
%>
<logic:redirect forward="Inicio" />
<%        } else {

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title><bean:message key="lb.title"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Bootstrap core CSS -->
        <link href="bootstrap/bootstrap.min.css" rel="stylesheet">

        <!-- Icon Set -->
        <link href="css/icon.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="css/font-awesome.min.css" rel="stylesheet">

        <!-- Animate Css -->
        <link href="css/animate.min.css" rel="stylesheet">

        <!-- Animsition -->
        <link href="css/animsition.min.css" rel="stylesheet">

        <!-- App min -->
        <link href="css/select2.min.css" rel="stylesheet">
        
        <!-- Select2 -->
        <link href="css/select2.min.css" rel="stylesheet">
        <!-- Core css -->
        <link href="css/app.css" rel="stylesheet" class="core-css">

        <!-- Jquery -->
        <script src="js/jquery-1.11.2.min.js"></script>


        <link href="codebase/dhtmlxgrid.css" rel="stylesheet" type="text/css" >
        <script src="codebase/dhtmlxgrid.js"></script>

        <link type="image/x-icon" href="img/logo.png" rel="shortcut icon"/>
        <!-- Datatable -->
        <link href="css/dataTables.bootstrap.css" rel="stylesheet"> 

        <!-- Calendar Css -->
        <link href="css/fullcalendar.min.css" rel="stylesheet">

        <!-- Animsition -->
        <link href="css/animsition.min.css" rel="stylesheet">

        <!-- WYSIWYG Editor -->
        <link href="css/summernote.min.css" rel="stylesheet">
        <!-- Moment -->
        <script src="js/moment.min.js"></script>
        <!-- Date Picker -->
        <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    </head>

    <body>

        <!-- /.wrapper -->
        <div class="wrapper animsition has-footer">

            <!-- Start header -->
            <header class="header-top navbar">


                <div class="navbar-header">
                    <button type="button" class="navbar-toggle side-nav-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                    <a class="navbar-brand" href="LoadInicio.appropiate">A<span>ppropiate</span></a>

                    <ul class="nav navbar-nav-xs">
                        <li>
                            <a href="#" class="font-lg collapse" data-toggle="collapse" data-target="#headerNavbarCollapse">
                                <i class="icon-user move-d-1"></i>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="search-toggle">
                                <i class="fa fa-search"></i>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="toggle-right-sidebar">
                                <i class="icon-menu2"></i>
                            </a>
                        </li>
                    </ul>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="headerNavbarCollapse">

                    <ul class="nav navbar-nav navbar-left">
                        <li class="user-profile dropdown">
                            <a href="#" class="clearfix dropdown-toggle" data-toggle="dropdown"> 
                                Entidad:
                            </a>
                        </li>
                        <li class="user-profile dropdown">
                            <a href="#" class="clearfix"> 
                                <select class="" id="idEntidadGlobal" onchange="changeEntidadGlobal(this.value)">
                                    <logic:iterate id="entidad" name="entidades">
                                        <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                    </logic:iterate>
                                </select>
                            </a>
                        </li>


                    </ul>


                    <ul class="nav navbar-nav navbar-right">

                        <li class="user-profile dropdown">
                            <a href="#" class="clearfix dropdown-toggle" data-toggle="dropdown"> 
                                <div class="user-name"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/> <span class="caret m-l-5"></span></div>
                            </a>
                            <ul class="dropdown-menu dropdown-animated pop-effect" role="menu">
                                <li><a href="#" onclick="loadPerfil()">Perfil</a></li>
                                <li><a href="#acercaDeModal" data-toggle="modal">Acerca de</a></li>
                                <li><a href="LogOutUser.appropiate">Salida segura</a></li>
                            </ul>
                        </li>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </header>
            <!-- End Header -->

            <!-- Start Side Navigation -->
            <aside class="side-navigation-wrap sidebar-fixed">
                <div class="sidenav-inner">
                    <ul class="side-nav magic-nav">
                        <li class="side-nav-header">
                            Menu
                        </li>
                        <li class="first-link active"><a href="LoadInicio.appropiate" class="animsition-link"><i class="icon-stats-dots"></i> <span class="nav-text"><bean:message key="system.dashboard"/></span></a></li>
                                <% out.print(request.getSession().getAttribute("menu"));%>   
                    </ul>
                </div><!-- /.sidebar-inner -->
            </aside>
            <!-- End Side Navigation -->


            <!-- End Right Sidebar -->

            <!-- End Second Level Right Sidebar -->

            <!-- Start Main Container -->
            <div class="main-container">
                <%--aqui va el contenido --%>
                <%String contentID = request.getAttribute("contenido").toString();%>
                <jsp:include page='<%=contentID%>' />
            </div>
            <!-- End Main Container -->

            <!-- Start Footer -->
            <!--            <footer class="footer">
                            &copy; 2015. <b>ConsulTIC'S.</b> by <b><a href="http://www.iammagis.com">Iam Magis S.A.S.</a></b>
                        </footer>-->
            <!-- End Footer -->


            <div class="modal modal-scale fade" id="acercaDeModal">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title font-header text-dark">Acerca de</h4>
                        </div>
                        <div class="modal-body" style="text-align: center;">
                            <div class="table-row" >
                                <div class="table-cell-row"> 
                                    <table style="width: 100%;">
                                        <tr>
                                            <td><a  href="http://www.iammagis.com" ><img src="img/iammagis.png"/></a></td>
                                        </tr>
                                    </table> 
                                </div> 
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    Desarrollado por Iam Magis S.A.S.  
                                </div>
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    Medellin - Colombia
                                </div>
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    <a style="color: #000088; margin:0 auto;" href="http://www.iammagis.com">www.iammagis.com</a> 
                                </div>
                            </div

                        </div>
                        <div class="modal-footer"> 
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- End Modal -->

        </div>

        <div class="modal fade" id="modal-perfil">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Perfil</h4>
                    </div>
                    <form name="Usuario" id="PerfilUsuario" enctype="multipart/form-data" method="post" accept-charset="utf-8">
                        <div class="modal-body"> 
                            <div class="modal-body">
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input type="hidden" name="idusuario" id="idusuarioP">
                                    <input class="form-control" type="text" name="nombre" id="nombreP" placeholder="<bean:message key="appropiate.nombre"/>" required="true">
                                </div>
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="text" name="apellidos" id="apellidosP" placeholder="<bean:message key="appropiate.apellidos"/>" required="true">
                                </div> 
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="email" name="email" id="emailP" placeholder="<bean:message key="appropiate.email"/>" required="true" readonly>
                                </div>
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="password" name="pass" id="passP" placeholder="<bean:message key="appropiate.pass"/>" required="true">
                                </div> 
                            </div> 
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><bean:message key="cerrar"/></button>
                            <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <div class="modal fade" id="modal-esperar">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                        <h4 class="modal-title">Espere</h4>
                    </div>

                    <div class="modal-body">
                        Cargando ...
                    </div>
                    <div class="modal-footer">

                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <div class="modal fade" id="modal-error">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Info</h4>
                    </div>

                    <div class="modal-body">
                        <p id="errorLabel"></p>
                    </div>
                    <div class="modal-footer">

                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <!--CONTENIDO GLOBAL-->
        <div id="show-pqrs-modal" class="modal modal-styled fade">
            <%-- por aca va el formulario de dita usuario --%>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title"><bean:message key="responder"/></h3>
                    </div>
                    <form id="responder-pqrs-modal" method="post" enctype="multipart/form-data">
                        <div class="modal-body" > 
                            <div class="form-group" style="text-align: center;">
                                <img id="img" style="width: 80%; height: auto;">
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="idsolicitud" id="idsolicitud-pqrs"/>
                                <label for="text-input"><bean:message key="appropiate.entidad"/></label>
                                <select id="idEntidad-pqrs" name="idEntidad" class="form-control select">
                                    <logic:iterate id="entidad" name="entidades">
                                        <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                    </logic:iterate>
                                </select> 
                            </div> 
                            <div class="form-group">
                                <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                                <input type="text" id="nombre-pqrs" readonly class="form-control"/>
                            </div>
                            <div class="form-group">
                                <label for="text-input"><bean:message key="appropiate.fecha"/></label> 
                                <div id="fechaStringEdit" class="input-group date"  >
                                    <input class="form-control" readonly type="text">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                </div> 
                                <input type="hidden" name="fecha" id="fechaPickerEdit-pqrs">
                            </div> 
                            <div class="form-group">
                                <label for="text-input"><bean:message key="appropiate.informacion"/></label>
                                <textarea name="informacion" id="informacion-pqrs" class="form-control"></textarea>
                            </div> 
                            <div class="form-group">
                                <label for="text-input"><bean:message key="appropiate.comentarios"/></label>
                                <div id="cometnario" style="width: 100%; height: 150px; overflow-y: scroll;" class="form-control">

                                </div>
                                <div >
                                    <input type="text" autocomplete="off" id="comentario-pqrs" placeholder="Ingrese un comentario." class="form-control" style="width: 90%; display: inline;">
                                    <button type="button" class="btn btn-primary" onclick="addComentario()" style="width: 9%;">+</button>
                                </div> 
                            </div> 
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                            <button type="button" onclick="finalizar()" class="btn btn-primary"><bean:message key="finalizar"/></button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <div id="show-modal-campana" class="modal modal-styled fade">
            <%-- por aca va el formulario de dita usuario --%>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title">Campaña</h3>
                    </div>

                    <div class="modal-body" > 
                        <div class="form-group" style="text-align: center;">
                            <img id="img" style="width: 80%; height: auto;">
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="idevento" id="idevento"/>
                            <label for="text-input"><bean:message key="appropiate.entidad"/></label>
                            <select id="idEntidad-campana" name="idEntidad" class="form-control select">
                                <logic:iterate id="entidad" name="entidades">
                                    <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div> 

                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre-campana" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.informacion"/></label>
                            <textarea name="informacion" id="informacion-campana" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.fecha"/></label> 
                            <div id="fechaStringEdit" class="input-group date" data-auto-close="true" data-date-format="dd-mm-yyyy" data-date-autoclose="true">
                                <input class="form-control" type="text">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            </div> 
                            <input type="hidden" name="fecha" id="fechaPickerEdit-campana">
                        </div>  
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button> 
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->  
        <div id="show-evento-modal" class="modal modal-styled fade">
            <%-- por aca va el formulario de dita usuario --%>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title"><bean:message key="editar"/></h3>
                    </div>

                    <div class="modal-body" > 
                        <div class="form-group" style="text-align: center;">
                            <img id="img" style="width: 80%; height: auto;">
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="idevento" id="idevento"/>
                            <label for="text-input"><bean:message key="appropiate.entidad"/></label>
                            <select id="idEntidad-evento" name="idEntidad" class="form-control select">
                                <logic:iterate id="entidad" name="entidades">
                                    <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div> 

                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre-evento" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.informacion"/></label>
                            <textarea name="informacion" id="informacion-evento" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.fecha"/></label> 
                            <div id="fechaStringEdit" class="input-group date" data-auto-close="true" data-date-format="dd-mm-yyyy" data-date-autoclose="true">
                                <input class="form-control" type="text">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            </div> 
                            <input type="hidden" name="fecha" id="fechaPickerEvento">
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.imagen"/></label> 
                            <input type="file" name="imagenform" required="true" class="form-control"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary"><bean:message key="editar"/></button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 


        <!--MODALES DE GESTIO-->
        <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="remove-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Desea eliminarlo ?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button> 
                        <button type="button" class="btn btn-default" onclick="confirmarEliminar()">Confirmar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap -->
        <script src="bootstrap/bootstrap.min.js"></script>

        <!-- Modernizr -->
        <script src="js/modernizr.min.js"></script>

        <!-- Slim Scroll -->
        <script src="js/jquery.slimscroll.min.js"></script>

        <!-- Animsition -->
        <script src="js/jquery.animsition.min.js"></script>

        <!-- Sparkline -->
        <script src="js/jquery.sparkline.min.js"></script>

        <!-- Flot -->
        <script src="js/jquery.flot.min.js"></script>
        <script src="js/jquery.flot.pie.js"></script>


        <!-- Simple Calendar -->
        <script src="js/uncompressed/simplecalendar.js"></script>

        <!-- Skycons -->
        <script src='js/uncompressed/skycons.js'></script>

        <!-- Noty -->
        <script src="js/jquery.noty.packaged.min.js"></script>

        <!-- Cookie -->
        <script src='js/uncompressed/jquery.cookie.js'></script>

        <!-- App -->
        <script src="js/app.min.js"></script>


        <!-- Datatable -->
        <script src="js/jquery.dataTables.min.js"></script>
        <script src="js/dataTables.bootstrap.min.js"></script> 

        <!-- WYSIWYG Editor -->
        <script src="js/summernote.min.js"></script>

        <!-- Colorbox -->
        <script src="js/jquery.colorbox-min.js"></script>  

        <!--select 2-->
        <script src="js/select2.min.js"></script>
        <!-- Date Picker -->
        <script src="js/bootstrap-datetimepicker.min.js"></script>
        <script>

                            var val = "<bean:write name="entidadGlobal" property="identidad"/>"; 
                            $("#idEntidadGlobal").val(val);

                            function changeEntidadGlobal(value) {
                                window.location.href = "ChangeEntity.appropiate?id=" + value;
                            }
//                            var id;
//                            function loadPerfil() {
//                                $('#modal-esperar').modal('show');
//                                $.ajax({
//                                    type: "POST",
//                                    cache: false,
//                                    url: 'LoadUserJson.appropiate',
//                                    data: {idUsuario: <bean:write name="usuario" property="idusuario"/>},
//                                    success: function (json) {
//                                        $('#modal-esperar').modal('hide');
//                                        if (json.error != null) {
//                                            $("#errorLabel").text(json.error);
//                                            $("#modal-error").modal("show");
//                                        } else {
//                                            $("#nombreP").val(json.nombre);
//                                            $("#apellidosP").val(json.apellidos);
//                                            $("#numeroDocumentoP").val(json.documento);
//                                            $("#movilP").val(json.movil);
//                                            $("#direccionP").val(json.direccion);
//                                            $("#emailP").val(json.correo);
//                                            $("#pass").val(json.pass);
//                                            $("#idusuarioP").val(json.id);
//                                            $("#modal-perfil").modal("show");
//                                        }
//                                    }
//                                });
//                            }
//                            function loadPqrs(ids) {
//                                id = ids;
//                                $.ajax({
//                                    url: "LoadSolicitudJson.appropiate",
//                                    data: {id: ids}
//                                }).done(function (json) {
//                                    $("#idsolicitud").val(json.id);
//                                    $("#identidad").val(json.identidad).change();
//                                    $("#nombre-pqrs").val(json.nombre);
//                                    $("#informacion-pqrs").text(json.informacion);
//                                    $("#cometnario").append(json.comentario);
//                                    var dateEdit = new Date(json.fecha);
//                                    $("#fechaPickerEdit").val(dateEdit.getTime());
//                                    $('#fechaStringEdit').datepicker("setDate", dateEdit).on('changeDate', function (selected) {
//                                        $(this).attr("disabled", "disabled");
//                                    });
//
//                                    $("#show-pqrs-modal").modal('show');
//                                });
//                            }
//                            function finalizar() {
//                                //AddComentarioSolicitud 
//                                $.ajax({
//                                    url: "FinalizarSolicitud.appropiate",
//                                    data: {
//                                        id: id
//                                    }
//                                }).done(function (json) {
//                                    $("#show-pqrs-modal").modal('show');
//                                    $("#show-pqrs-modal").modal("hide");
//                                    mygrid.clearAll();
//                                    mygrid.parse(json.grid, "json");
//                                });
//                            }
//                            function addComentario() {
//                                if ($("#comentario").val() == "") {
//                                    $("#errorLabel").text("Ingrese un comentario.");
//                                    $("#modal-error").modal("show");
//                                    return;
//                                }
//                                //AddComentarioSolicitud 
//                                $.ajax({
//                                    url: "AddComentarioSolicitud.appropiate",
//                                    data: {
//                                        id: id,
//                                        comentario: $("#comentario").val()
//                                    }
//                                }).done(function (json) {
//                                    $("#comentario").val('');
//                                    $("#cometnario").empty().append(json.comentario);
//                                    $("#show-pqrs-modal").modal('show');
//                                });
//                            }
//                            function loadCampana(ids) {
//                                $.ajax({
//                                    url: "LoadCampanaJson.appropiate",
//                                    data: {id: ids}
//                                }).done(function (json) {
//                                    $("#idevento").val(json.id);
//                                    $("#identidad").val(json.identidad).change();
//                                    $("#nombre-campana").val(json.nombre);
//                                    $("#informacion-campana").text(json.informacion);
//                                    $("#archivo").val(json.apellidos);
//                                    $("#img").attr("src", json.img);
//                                    var dateEdit = new Date(json.fecha);
//                                    $("#fechaPickerEdit").val(dateEdit.getTime());
//                                    $('#fechaStringEdit').datepicker("setDate", dateEdit);
//                                    $('#fechaStringEdit').datepicker().on('changeDate', function (ev) {
//                                        var date = new Date(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate(), 0, 0, 0);
//                                        if (date != null && date != 'undefined') {
//                                            $("#fechaPickerEdit").val(date.getTime());
//                                        }
//                                    });
//                                    $("#show-modal-campana").modal('show');
//                                });
//                            }
//
//                            function loadEvento(ids) {
//                                alert("loadEvento: " + ids);
//                                $.ajax({
//                                    url: "LoadEventoJson.appropiate",
//                                    data: {id: ids}
//                                }).done(function (json) {
//                                    $("#idevento").val(json.id);
//                                    $("#identidad").val(json.identidad).change();
//                                    $("#nombre-evento").val(json.nombre);
//                                    $("#informacion-evento").text(json.informacion);
//                                    $("#archivo").val(json.apellidos);
//                                    $("#img").attr("src", json.img);
//                                    var dateEdit = new Date(json.fecha);
//                                    $("#fechaPickerEvento").val(dateEdit.getTime());
//                                    $('#fechaStringEdit').datepicker("setDate", dateEdit);
//                                    $('#fechaStringEdit').datepicker().on('changeDate', function (ev) {
//                                        var date = new Date(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate(), 0, 0, 0);
//                                        if (date != null && date != 'undefined') {
//                                            $("#fechaPickerEvento").val(date.getTime());
//                                        }
//                                    });
//                                    $("#show-evento-modal").modal('show');
//                                });
//                            }


        </script>
    </body>
</html>
<%
    }
%>