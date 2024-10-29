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
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="appropiate.eventos"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group m-r-10" role="group">
                    <div class="input-link-in">
                        <input class="form-control input-rounded input-sm" placeholder="Buscar">
                        <a href="#" class="link-in"><i class="icon-search"></i></a>
                    </div>
                </div>

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus" ></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.eventos"/></a></li> 
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
                        <input type="hidden" name="idevento" id="idevento"> 
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
                            <label for="text-input"><bean:message key="appropiate.fecha"/></label>
                            <input type="text"  title="Seleccione una fecha" id="fecha" required="true" class="form-control"/> 
                            <input type="hidden" id="fechaHidden" name="fecha">
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.informacion"/></label>  
                            <!--<div id="informacionEditor" name="informacion">Ingrese la informacion del evento</div>--> 
                            <textarea type="text" class="form-control" name="informacion" id="informacion"></textarea>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="appropiate.foto"/></label>
                            <input type="file" name="imagenform" id="foto" required="true" class="form-control"/>
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
    <script src="js-in/eventos.js"></script>
    <script>
                                            $(function () {
                                                init(<%=request.getAttribute("grid")%>);
                                            });
    </script>
</html>
