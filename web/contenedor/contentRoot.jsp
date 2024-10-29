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
            <div class="col-sm-4">
                <div class="panel">
                    <div class="panel-body p-20 text-center">
                        <div class="h3 no-m font-header"><bean:write name="pqrsActivos"/></div>
                        <div class="font-12 m-t-5">P.Q.R.S. activos</div>
                    </div>
                </div>
            </div><!-- /.col -->
            <div class="col-sm-4">
                <div class="panel bg-main">
                    <div class="panel-body p-20 text-center">
                        <div class="h3 no-m font-header"><bean:write name="reservasPendientes"/></div>
                        <div class="font-12 m-t-5">Reservas pendientes</div>
                    </div>
                </div>
            </div><!-- /.col -->
            <div class="col-sm-4">
                <div class="panel">
                    <div class="panel-body p-20 text-center">
                        <div class="h3 no-m font-header"><bean:write name="pagosPendientes"/></div>
                        <div class="font-12 m-t-5">Pagos pendientes</div>
                    </div>
                </div>
            </div><!-- /.col -->
        </div><!-- /.row --> 



        <div class="row">
            <div class="col-sm-12">
                <div class="panel panel-default">
                    <div class="table-responsive">
                        <table class="table table-striped font-12" id="datatable">

                        </table>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-lg-6">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row font-12">
                            <div class="col-xs-9">
                                <span class="text-upper font-header">Global encuestas</span> 
                            </div>
                            <div class="col-xs-3 text-right">
                                <select class="select">
                                    <logic:iterate name="encuestas" id="encuesta">
                                        <option value="<bean:write name="encuesta" property="idencuesta"/>"><bean:write name="encuesta" property="nombre"/></option>
                                    </logic:iterate>
                                </select>
                            </div>
                        </div>
                        <div class="chart-placeholder line-placeholder m-t-10"></div>
                    </div>
                    <div class="panel-footer bg-white">
                        <div class="row text-center">
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header"><bean:write name="envios_encuesta"/></h4>
                                <div class="font-12 m-t-5 m-b-5">Numero de envios</div>
                            </div>
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header"><bean:write name="aperturas_encuesta"/></h4>
                                <div class="font-12 m-t-5 m-b-5">Numero de aperturas</div>
                            </div> 
                        </div>
                    </div>
                    <div class="loading-wrap">
                        <div class="loading-dots">
                            <div class="dot1"></div>
                            <div class="dot2"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row font-12">
                            <div class="col-xs-9">
                                <span class="text-upper font-header">Global eventos</span> 
                            </div>
                            <div class="col-xs-3 text-right">
                                <select class="select">
                                    <logic:iterate name="eventos" id="evento">
                                        <option value="<bean:write name="evento" property="idevento"/>"><bean:write name="evento" property="nombre"/></option>
                                    </logic:iterate>
                                </select>
                            </div>
                        </div>
                        <div class="chart-placeholder line-placeholder-2 m-t-10"></div>
                    </div>
                    <div class="panel-footer bg-white">
                        <div class="row text-center">
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header"><bean:write name="envios_evento"/></h4>
                                <div class="font-12 m-t-5 m-b-5">Numero de envios</div>
                            </div>
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header"><bean:write name="aperturas_evento"/></h4>
                                <div class="font-12 m-t-5 m-b-5">Numero de aperturas</div>
                            </div> 
                        </div>
                    </div>
                    <div class="loading-wrap">
                        <div class="loading-dots">
                            <div class="dot1"></div>
                            <div class="dot2"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.row -->

  


    </div>


    <div id="responder-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"><bean:message key="responder"/></h3>
                </div>
                <form id="responder" method="post" enctype="multipart/form-data">
                    <div class="modal-body" > 
                        <div class="form-group" style="text-align: center;">
                            <img id="img" style="width: 80%; height: auto;">
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="idsolicitud" id="idsolicitud"/>
                            <label for="text-input"><bean:message key="appropiate.entidad"/></label>
                            <select id="idEntidad" name="idEntidad" class="form-control select">
                                <logic:iterate id="entidad" name="entidades">
                                    <option value="<bean:write name="entidad" property="identidad"/>"><bean:write name="entidad" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" id="nombre" readonly class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.fecha"/></label> 
                            <div id="fechaStringEdit" class="input-group date"  >
                                <input class="form-control" readonly type="text">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            </div> 
                            <input type="hidden" name="fecha" id="fechaPickerEdit">
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.informacion"/></label>
                            <textarea name="informacion" id="informacion" class="form-control"></textarea>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.comentarios"/></label>
                            <div id="cometnario" style="width: 100%; height: 150px; overflow-y: scroll;" class="form-control">

                            </div>
                            <div >
                                <input type="text" autocomplete="off" id="comentario" placeholder="Ingrese un comentario." class="form-control" style="width: 90%; display: inline;">
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
    <script src="js-in/inicio.js"></script>
    <script type="text/javascript">
                            $(document).ready(function () {
                                init(<%=request.getAttribute("grid")%>);
                                initChart(<%=request.getAttribute("char_encuesta")%>,<%=request.getAttribute("char_evento")%>);
                            });

    </script>
</html>



