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
        Lugares y establecimientos
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group m-r-10" role="group">
                    <div class="input-link-in">
                        <input class="form-control input-rounded input-sm" placeholder="Buscar">
                        <a href="#" class="link-in"><i class="icon-search"></i></a>
                    </div>
                </div>

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <div class="content-wrap">
        <div class="search-result-wrap" id="result">

        </div><!-- /.search-result-wrap -->
    </div><!-- /.content-wrap -->

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
                        <input type="hidden" name="idlocacion" id="idlocacion"> 
                        <div class="form-group" style="text-align: center;"> 
                            <img id="img" style="width: 80%;">
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.responsable"/></label>
                            <input type="text" name="responsable" id="responsable" required="true" class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.foto"/></label>
                            <input type="file" name="imagenform" id="foto" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.descripcion"/></label>   
                            <textarea type="text" class="form-control" name="descripcion" id="descripcion"></textarea>
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
    <script src="js-in/locaciones_reservas.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>
</html>
