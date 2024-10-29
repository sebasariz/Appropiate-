<%-- 
    Document   : solicituds
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
        <bean:message key="appropiate.solicitudes.hisotiral"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group m-r-10" role="group">
                    <div class="input-link-in">
                        <input class="form-control input-rounded input-sm" id="campo" placeholder="Buscar">
                        <a href="#" class="link-in"><i class="icon-search" onclick="search()"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.solicitudes.hisotiral"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="search-result-wrap" id="result">

        </div><!-- /.search-result-wrap -->
    </div><!-- /.content-wrap -->


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

    <script src="js-in/historial_pqrs.js"></script>
    <script type="text/javascript">
                                            $(function () {
                                                //Custom Select
                                                init();
                                            });

    </script> 

</html>
