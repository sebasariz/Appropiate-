<%-- 
    Document   : eventos
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

    <div class="page-header font-header no-breadcrumb">
        Resultado eventos
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group m-r-10" role="group">
                    <div class="input-link-in">
                        <input class="form-control input-rounded input-sm" placeholder="Buscar">
                        <a href="#" class="link-in"><i class="icon-search"></i></a>
                    </div>
                </div>

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#">Resultado eventos</a></li> 
    </ol>

    <div class="content-wraps">
        <ul class="galleries list-unstyled clearfix"> 
            <% out.print(request.getAttribute("galeria"));%>    
        </ul>
    </div><!-- /.content-wrap -->

    <div id="resumen-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg" style="width: 650px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Resumen (<label id="numeroPersonas"></label>)</h3>
                </div>
                <div class="modal-body" > 
                    <div class="row">
                        <div class="col-md-6">
                            <table class="table table-striped font-12" id="datatable">
                                <thead>
                                    <tr>
                                        <td>Nombre</td>
                                        <td>Fecha</td>
                                    </tr>
                                </thead>
                                <tbody id="tbody-usuarios">

                                </tbody>
                            </table> 
                        </div>

                        <div class="col-md-6" style="text-align: center;">
                            <table class="table table-striped font-12 " id="datatable">
                                <thead>
                                    <tr>
                                        <td>Archivo</td>
                                    </tr>
                                </thead>
                                <tbody id="tbody-archivos">

                                </tbody>
                            </table>
                        </div>

                        <div class="col-lg-10">
                            <table class="table table-striped font-12" id="datatable">
                                <thead>
                                    <tr>
                                        <td>Usuario</td>
                                        <td>Comentario</td>
                                    </tr>
                                </thead>
                                <tbody id="tbody-comentarios">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button> 
                </div> 
            </div>
        </div>
    </div>
    <script type="text/javascript">

        function loadEvento(id) { 
            $('#modal-esperar').modal('show');
            $.ajax({
                type: "POST",
                cache: false,
                url: 'ResultadoEvento.appropiate',
                data: {id: id},
                success: function (json) {
                    $('#modal-esperar').modal('hide');
                    if (json.error) {
                        $("#errorLabel").text(json.error);
                        $("#modal-error").modal("show");
                    } else {
                        $("#numeroPersonas").text("Personas: " + json.personas);
                        $("#tbody-usuarios").empty().append(json.tableUsuarios);
                        $("#tbody-comentarios").empty().append(json.tableComentarios);
                        $("#tbody-archivos").empty().append(json.tableArchivos);
                        $("#resumen-modal").modal("show");


                    }
                }
            });
        }



    </script>
</html>
