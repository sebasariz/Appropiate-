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
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="appropiate.resultado.encuestas"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">
                <div class="btn-group " role="group" style="width: 300px;"> 
                    <div class="col-sm-10">
                        <select id="select" class="form-control chosen-select font-12 pull-right">
                            <logic:iterate id="encuesta" name="encuestas">
                                <option value="<bean:write name="encuesta" property="idencuesta"/>"><bean:write name="encuesta" property="nombre"/></option>
                            </logic:iterate>
                        </select>

                    </div>  
                </div>
                <div class="btn-group " role="group" style="width: 80px;"> 
                    <button type="button" onclick="search()" class="btn btn-success">Buscar</button>
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.resultado.encuestas"/></a></li> 
    </ol>

    <div class="content-wrap">
        <div class="col-sm-6">
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel">
                        <div class="panel-body p-20">
                            <div class="text-dark h4 no-m font-header" id="invitados">0</div>
                            <div class="font-12">Total de invitados</div>
                            <div class="sparkbar m-t-5"></div>
                        </div>
                    </div>
                </div><!-- /.col -->
                <div class="col-sm-12">
                    <div class="panel bg-main">
                        <div class="panel-body p-20">
                            <div class="h4 no-m font-header" id="parciales">0</div>
                            <div class="font-12">Encuestados parciales</div>
                            <div class="sparkline m-t-5"></div>
                        </div>
                    </div>
                </div><!-- /.col -->  
                <div class="col-sm-12">
                    <div class="panel">
                        <div class="panel-body p-20">
                            <div class="text-dark h4 no-m font-header" id="respuestas">0</div>
                            <div class="font-12">Numero de respuestas</div>
                            <div class="sparkline m-t-5"></div>
                        </div>
                    </div>
                </div><!-- /.col --> 

                <div class="col-sm-6">
                    <div class="panel bg-gray">
                        <div class="panel-body p-20">
                            <div class="font-12">Descargar resumen</div>
                            <div class="sparkline m-t-5"></div>
                        </div>
                    </div>
                </div><!-- /.col --> 

                <div class="col-sm-6">
                    <div class="panel bg-success">
                        <div class="panel-body p-20">
                            <div class="font-12">Descargar detalle</div>
                            <div class="sparkline m-t-5"></div> 
                        </div>
                    </div>
                </div><!-- /.col --> 
            </div><!-- /.row --> 
        </div><!-- /.col -->



        <div class="col-sm-6">
            <div class="panel panel-default panel-stat">
                <div class="content-wrap b-b">
                    <div class="value stat-icon">
                        <span><i class="icon-download"></i></span>
                    </div>
                    <div class="detail text-right">
                        <h3 class="text-upper font-header no-m">Preguntas</h3>
                        <!--<small class="text-muted">Fecha finalizacion</small>-->
                    </div>
                </div> 
                <table class="table table-bordered font-12">
                    <tbody id="tbody">
                            

                    </tbody>
                </table>
            </div>
        </div><!-- /.col -->
    </div>
    
    <script src="js-in/resultado_encuestas.js"></script>
    <script type="text/javascript">
        $(function () {
            //Custom Select
            init();
        });

    </script> 




</html>
