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
        Facturas
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar"> 
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="xls"><i class="fa fa-database "></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#">Facturas</a></li> 
    </ol>

    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Facturas</div>
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
                        <input type="hidden" name="idfactura" id="idfactura"> 
                        <input type="hidden" name="conceptos" id="conceptos"> 
                        <div class="row">
                            <div class="col-sm-6">
                                <label for="text-input">Cliente o facturado</label>
                                <select name="idUsuario" id="idUsuario" class="form-control select">
                                    <option value="0">Seleccione un usuario</option>
                                    <logic:iterate id="usuario" name="usuarios">
                                        <option value="<bean:write name="usuario" property="idusuario"/>"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/> (<bean:write name="usuario" property="correo"/>)</option>
                                    </logic:iterate>
                                </select>
                            </div>
                            <div class="col-sm-6">
                                <label for="text-input">Referencia</label>
                                <input type="text" name="referencia" id="referencia" required="true" class="form-control"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6">
                                <label for="text-input">Plantilla</label>
                                <select name="idTemplate" id="idTemplate" class="form-control select">
                                    <option value="0">Seleccione una plantilla</option>
                                    <logic:iterate id="template" name="templates">
                                        <option value="<bean:write name="template" property="idfacturaTemplate"/>"><bean:write name="template" property="nombre"/> </option>
                                    </logic:iterate>
                                </select>
                            </div>
                            <div class="col-sm-6"> 
                                <label for="text-input"><bean:message key="appropiate.fecha"/></label>
                                <input type="text"  title="Seleccione una fecha" id="fecha" required="true" class="form-control"/> 
                                <input type="hidden" id="fechaHidden" name="fecha">
                            </div>
                        </div> 

                    </div> 


                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12">
                                <label for="text-input">Items</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="item" placeholder="Item">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="unidades" placeholder="Unidades">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="valor" placeholder="Valor">
                            </div> 
                            <div class="col-sm-3">
                                <button class="btn btn-primary" type="button" onclick="agregar()" style="width: 100%;">+</button>
                            </div>
                        </div>

                        <table class="table table-striped font-12" id="datatableConceptos">

                        </table> 
                    </div> 
                    <div class="modal-body" >  
                        <div class="row">
                            <div class="col-sm-9">
                            </div>
                            <div class="col-sm-3">
                                <label for="text-input">Valor total</label>
                                <input type="text" name="valor" id="valortotal" required="true" class="form-control"/>
                            </div>
                        </div> 

                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>


    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="pagar-modal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Desea marcar como pagada esta factura?</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button> 
                    <button type="button" class="btn btn-default" onclick="ConfirmarMarcarPaga()">Confirmar</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="recordar-modal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Desea enviar recordatorio de pago?</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button> 
                    <button type="button" class="btn btn-default" onclick="RecordatorioPago()">Confirmar</button>
                </div>
            </div>
        </div>
    </div>
    <script src="js-in/facturas.js"></script>
    <script>
                        $(function () {
                            init(<%=request.getAttribute("grid")%>);
                        });
    </script>
</html>
