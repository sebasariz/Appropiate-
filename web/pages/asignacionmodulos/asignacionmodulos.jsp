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
        <bean:message key="appropiate.asignacionmodulos"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.asignacionmodulos"/></a></li> 
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
        <div class="modal-dialog modal-fluid">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Modulos</h3>
                </div> 
                <div class="modal-body" > 
                    <table class="table table-striped font-12" id="datatable-modulos"> 
                    </table>
                </div> 
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <script src="js-in/asignacion_modulos.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>



</script>
</html>
