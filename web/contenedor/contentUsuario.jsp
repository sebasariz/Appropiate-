<%-- 
    Document   : content
    Created on : 8/11/2011, 12:41:25 PM
    Author     : sebasariz
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!-- create the DOM node for the chart -->
<html>
    <div class="page-header no-breadcrumb font-header"><bean:message key="system.dashboard"/></div>
    <div class="content-wrap">
        <div class="row">
            <div class="panel panel-default panel-stat no-icon">
                <div class="bg-main content-wrap b-b">
                    <div class="value">
                        <h2 class="font-header no-m">Utimos PQR</h2>
                    </div> 
                </div> 
                <table class="table table-bordered font-12">
                    <thead>
                        <tr>
                            <th data-filterable="false">Select</th>
                            <th data-filterable="false" data-sortable="true">Solicitante</th>
                            <th data-filterable="false" data-sortable="true">Fecha</th>
                            <th data-filterable="false" data-sortable="true">Tipo</th>
                            <th data-filterable="false" data-sortable="true">Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% out.print(request.getAttribute("pqrs"));%>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-stat no-icon">
                <div class="bg-main content-wrap b-b">
                    <div class="value">
                        <h2 class="font-header no-m">Campañas Activas</h2>
                    </div> 
                </div> 
                <table class="table table-bordered font-12">
                    <thead>
                        <tr>
                            <th data-filterable="false">Select</th>
                            <th data-filterable="false" >Nombre</th>
                            <th data-filterable="false" >Fecha</th>
                            <th data-filterable="false" >Visto por</th>

                        </tr>
                    </thead>
                    <tbody>
                        <% out.print(request.getAttribute("campanas"));%>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-stat no-icon">
                <div class="bg-main content-wrap b-b">
                    <div class="value">
                        <h2 class="font-header no-m">Eventos activos</h2>
                    </div> 
                </div> 
                <table class="table table-bordered font-12">
                    <thead>
                        <tr>
                            <th data-filterable="false">Select</th>
                            <th data-filterable="false" >Nombre</th>
                            <th data-filterable="false" >Fecha</th>
                            <th data-filterable="false" >Asistentes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% out.print(request.getAttribute("eventos"));%>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-stat no-icon">
                <div class="bg-main content-wrap b-b">
                    <div class="value">
                        <h2 class="font-header no-m">Encuestas activas</h2>
                    </div> 
                </div> 
                <table class="table table-bordered font-12">
                    <thead>
                        <tr>
                            <th data-filterable="false">Select</th>
                            <th data-filterable="false" >Nombre</th>
                            <th data-filterable="false" >Respuestas</th>
                            <th data-filterable="false" >Resumen rapido</th>
                            <th data-filterable="false" >Resumen detallado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% out.print(request.getAttribute("encuestas"));%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
     
 
    <script type="text/javascript">
        $(document).ready(function () {
            //tipo de usuario
            $(".select").select2();
            $("#libranza").submit(function (e) {
                e.preventDefault();
                var formData = new FormData(this);
                $("#modal-esperar").modal('show');
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: 'CreateLibranzaGlobal.lb',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (json) {
                        $("#modal-esperar").modal('hide');
                        if (json.error != null) {
                            $("#errorLabel").text(json.error);
                            $("#modal-error").modal("show");
                        } else {
                            $("#crear-modal").modal('hide');

                        }
                    }
                });
            });
        });
        function createOrden() {
            $("#crearOrden").modal("show");
        }

    </script>
</html>



