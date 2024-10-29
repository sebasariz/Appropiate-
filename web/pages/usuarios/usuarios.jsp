<%-- 
    Document   : usuarios
    Created on : 26/09/2012, 04:50:53 PM
    Author     : sebasariz
--%>

<%@page pageEncoding="UTF-8" contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
    <style>
        .select2 {
            width:100%!important;
        }
    </style>
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="appropiate.usuarios"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.usuarios"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div> 

    <!--MODAL GESTION--> 
    <div id="gestion-modal-grupos" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div> 
                <div class="modal-body" > 
                    <table class="table table-striped font-12" id="datatable-grupos">

                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button> 
                </div>
            </div>
        </div>
    </div>

    <!--MODAL GESTION--> 
    <div id="gestion-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <form id="gestion" method="post" enctype="multipart/form-data">
                    <div class="modal-body" > 
                        <input type="hidden" name="idUsuario" id="idusuario">
                        <input type="hidden" name="idEntidadString" id="idEntidadString">
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.tipousuario"/></label>
                            <select name="idTipoUsuario" id="idTipoUsuario" class="form-control chosen-select select">
                                <logic:iterate id="tipo" name="tipos">
                                    <option value="<bean:write name="tipo" property="idtipoUsuario"/>"><bean:write name="tipo" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div>

                        <div class="form-group" id="entidad" >
                            <label for="text-input"><bean:message key="appropiate.entidad"/></label>

                            <select name="idEntidad" id="idEntidad" class="form-control chosen-select select" multiple>
                                <logic:iterate id="entidad" name="entidades">
                                    <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div>

                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.apellidos"/></label>
                            <input type="text" name="apellidos" id="apellidos" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.email"/></label> 
                            <input type="email" name="correo" id="correo" required="true" class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.pass"/></label> 
                            <input type="text" name="pass" id="pass" required="true"  class="form-control"/>
                        </div> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 


    <script src="js-in/usuarios.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>


</script>
</html>
