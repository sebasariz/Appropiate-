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


    <div class="page-header font-header">
        Plantilla de facturas
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus" ></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#">Plantilla de facturas</a></li> 
    </ol>

    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Plantillas</div>
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div><!-- /.content-wrap -->


    <div id="gestion-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <form id="gestion" method="post" enctype="multipart/form-data">
                    <div class="modal-body" > 
                        <input type="hidden" name="idfacturaTemplate" id="idfacturaTemplate"> 
                        <input type="hidden" name="campos" id="campos"> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.plantilla"/></label>
                            <textarea type="text" name="template" id="template" required="true" class="form-control" style="min-height: 300px;"></textarea>
                        </div>

                    </div> 
                    <div class="panel-body">

                        <div class="row">
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="variable" placeholder="Nombre Varaible $variable">
                            </div>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="xls" placeholder="Columna XLS A=0"> 
                            </div> 
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="agregar()" style="width: 100%;">+</button>
                            </div>
                        </div>

                        <table class="table table-striped font-12" id="datatablecampos">

                        </table> 
                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <div id="preview-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>

                <div class="panel-body">

                    <div class="row" id="html">

                    </div>

                </div> 
                <div class="modal-footer">
                    <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                </div> 
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <script src="js-in/planilla_facturas.js"></script>
    <script>
                                    $(function () {
                                        init(<%=request.getAttribute("grid")%>);
                                    });
    </script>

</html>
