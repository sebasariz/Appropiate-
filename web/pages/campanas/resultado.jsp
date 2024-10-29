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
        <bean:message key="appropiate.resultado.campanas"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group m-r-10" role="group">
                    <div class="input-link-in">
                        <input class="form-control input-rounded input-sm" placeholder="Buscar">
                        <a href="#" class="link-in"><i class="icon-search"></i></a>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="appropiate.resultado.campanas"/></a></li> 
    </ol>




    <div class="content-wrap">
        <div class="row">
            <div class="col-md-4">
                <div class="panel no-b">
                    <div class="panel-heading font-header bg-main p-tb-30">
                        <div class="text-center"><img src="../img/profile2.jpg" class="img-circle img-70" alt=""></div>
                        <div class="m-t-10 text-center">
                            @JenniferEvans
                            <div class="font-11 text-muted">Web Designer</div>
                        </div>
                    </div>
                    <div class="panel-body no-p">
                        <ul class="list-unstyled list-float list-bordered three-col clearfix b-t no-m-b">
                            <li class="p-10 text-center font-12">
                                <i class="icon-file-text2 m-d-1 m-r-5"></i>85
                            </li>
                            <li class="p-10 text-center font-12">
                                <i class="icon-star-full m-r-5"></i>102
                            </li>
                            <li class="p-10 text-center font-12">
                                <i class="icon-heart m-r-5"></i> 99
                            </li>
                        </ul>
                    </div>
                    <div class="panel-footer no-p no-b-t bg-white">
                        <ul class="list-unstyled list-float list-bordered two-col clearfix b-all b-t no-m-b">
                            <li class="p-10 p-tb-10 text-center font-12 pointer">
                                <div class="font-header text-upper">Detalles</div>
                            </li>
                            <li class="p-10 p-tb-10 text-center font-12 pointer">
                                <div class="font-header text-upper">Resultados</div>
                            </li>
                        </ul>
                    </div>
                </div><!-- /.panel -->
            </div><!-- /.col -->
            
            <div class="col-md-4">
              <div class="panel no-b">
                <div class="panel-heading font-header bg-main p-tb-30">
                  <div class="text-center"><img src="../img/profile2.jpg" class="img-circle img-70" alt=""></div>
                  <div class="m-t-10 text-center">
                    @JenniferEvans
                    <div class="font-11 text-muted">Web Designer</div>
                  </div>
                </div>
                <div class="panel-body no-p">
                  <ul class="list-unstyled list-float list-bordered three-col clearfix b-t no-m-b">
                    <li class="p-10 text-center font-12">
                      <i class="icon-file-text2 m-d-1 m-r-5"></i>85
                    </li>
                    <li class="p-10 text-center font-12">
                      <i class="icon-star-full m-r-5"></i>102
                    </li>
                    <li class="p-10 text-center font-12">
                      <i class="icon-heart m-r-5"></i> 99
                    </li>
                  </ul>
                </div>
                <div class="panel-footer no-p no-b-t bg-white">
                  <ul class="list-unstyled list-float list-bordered two-col clearfix b-all b-t no-m-b">
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                      <div class="font-header text-upper">Detalles</div>
                    </li>
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                      <div class="font-header text-upper">Resultados</div>
                    </li>
                  </ul>
                </div>
              </div><!-- /.panel -->
            </div><!-- /.col -->
            
        </div>
    </div>

</html>
