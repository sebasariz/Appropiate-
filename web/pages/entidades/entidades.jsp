<%-- 
    Document   : entidads
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
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="appropiate.entidades"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon"  id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.entidades"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div><!-- /.content-wrap --> 


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
                        <input type="hidden" name="identidad" id="identidad"> 


                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.contacto"/></label>
                            <input type="text" name="contacto" id="contacto" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.direccion"/></label> 
                            <input type="text" name="direccion" id="direccion" required="true" class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nit"/></label> 
                            <input type="text" name="nit" id="nit" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.telefono"/></label> 
                            <input type="text" name="telefono" id="telefono" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.correo"/></label> 
                            <input type="email" name="correo" id="correo" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.identificador"/></label> 
                            <input type="text" name="identificador" id="identificador" readonly required="true"  class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.reservas"/></label> 
                            <select name="reservas" id="reservas" class="form-control">
                                <option value="0">No</option>
                                <option value="1">Si</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.pasarela"/></label> 
                            <select name="pasarela" id="pasarela" class="form-control">
                                <option value="0">Sin facturacion</option>
                                <option value="1">Epayco</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.llaves"/></label> 
                            <input type="text" name="camposPasarela" id="camposPasarela" class="form-control"/>
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


    <script src="js-in/entidades.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>

</html>
