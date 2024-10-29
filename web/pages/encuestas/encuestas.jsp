<%-- 
    Document   : campanas
    Created on : 19-feb-2018, 9:38:29
    Author     : sebastianarizmendy
--%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html> 
    <style>
        .select2-container--default .select2-selection--multiple{
            height: auto;
        }
    </style>
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="appropiate.encuestas"/>
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
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.encuestas"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="search-result-wrap" id="result"> 
        </div><!-- /.search-result-wrap -->
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
                        <input type="hidden" name="idencuesta" id="idencuesta"> 
                        <input type="hidden" name="idparaString" id="idparaString">
                        <div class="form-group" style="text-align: center;"> 
                            <img id="img" style="width: 80%;">
                        </div>
                        <div class="form-group" id="entidad" >
                            <label for="text-input"><bean:message key="appropiate.para"/></label>

                            <select  id="idpara" class="form-control chosen-select select" multiple style="height: auto;">
                                <logic:iterate id="para" name="paras">
                                    <option value="<bean:write name="para" property="id"/>"><bean:write name="para" property="nombre"/></option>
                                </logic:iterate>
                            </select> 
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.foto"/></label>
                            <input type="file" name="foto" id="foto" required="true" class="form-control"/>
                            <input type="hidden" name="camposJson" id="camposJson"/>
                        </div> 
                        <div class="panel">
                            <div class="panel-body">

                                <div class="row">
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="pregunta" placeholder="<bean:message key="appropiate.pregunta"/>">
                                    </div>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="tipo">
                                            <option value="0">Selceccione un tipo</option>
                                            <option value="1">Si/No</option>
                                            <option value="2">Abierta</option>
                                            <option value="3">Escala</option>
                                            <option value="4">Multiple</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2" >
                                        <input type="text" class="form-control" id="escala" placeholder="<bean:message key="appropiate.escala"/>">
                                    </div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-primary" type="button" onclick="agregar()">+</button>
                                    </div>
                                </div>

                                <table class="table table-striped font-12" id="datatable">

                                </table> 
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
    </div><!-- /.modal --> 


    <div id="comentarios-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Comentarios</h3>
                </div>
                <div class="modal-body" style="height: 500px;">  
                    <div class="app-wrapper chat-wrapper  app-0-100"> 
                        <div class="app-col app-content-col chat-content bg-white">
                            <div class="loading-wrap">
                                <div class="loading-dots">
                                    <div class="dot1"></div>
                                    <div class="dot2"></div>
                                </div>
                            </div>

                            <div class="app-scrollable-content2">
                                <div class="chat-item2s font-12" id="comentarios">

                                </div><!-- /.chat-item2s -->
                            </div><!-- /.app-scrollable-content -->
                            <div class="chat-reply bg-white">
                                <div class="input-group"> 
                                    <input type="text" id="comentarioString" placeholder="Ingrese un comentario" class="form-control input-sm">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default btn-sm" onclick="saveComment()" type="button">enviar</button>
                                    </span>
                                </div>
                            </div><!-- /.chat-reply -->
                        </div>
                    </div><!-- /.task-wrapper --> 
                </div>  
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

    <script src="js-in/encuestas.js"></script>
    <script>
                                            $(function () {
                                                init(<%=request.getAttribute("grid")%>);
                                            });
    </script>

</html>
