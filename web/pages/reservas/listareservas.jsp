

<%@page pageEncoding="UTF-8" contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html lang="en"> 
    <body> 
        <div class="page-header font-header">Reservas</div>
        <ol class="breadcrumb">
            <li><a href="#">Inicio</a></li>
            <li><a href="#">Reservas</a></li> 
        </ol> 
        <div class="content-wrap">
            <div class="panel panel-default">
                <div class="panel-body p-30">
                    <div id="fullCalendar"></div>
                </div>
            </div>
        </div><!-- /.content-wrap -->

        <!-- End Main Container -->


        <div class="modal fade" id="addEventModal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Agregar Reserva</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>Fecha y hora</label>
                                <input type="text"  title="Seleccione una fecha" id="fecha" required="true" class="form-control"/>  
                            </div>  
                            <div class="form-group">
                                <label>Locación</label>
                                <select id="newLocation" class="form-control select">
                                    <option value="0">Seleccione una locación</option>
                                    <logic:iterate id="locacion" name="locaciones">
                                        <option value="<bean:write name="locacion" property="idlocacion"/>"><bean:write name="locacion" property="nombre"/></option>
                                    </logic:iterate>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Persona</label>
                                <select id="newUser" class="form-control select">
                                    <option value="0">Seleccione una persona</option>
                                    <logic:iterate id="usuario" name="usuarios">
                                        <option value="<bean:write name="usuario" property="idusuario"/>"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/> (<bean:write name="usuario" property="correo"/>)</option>
                                    </logic:iterate>
                                </select>
                            </div>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-main" id="addEventBtn">Agregar</button>
                        <button type="button" class="btn btn-gray" data-dismiss="modal">Cerrar</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div class="modal fade" id="updateEventModal">
            <div class="modal-dialog modal-md">
                <div class="modal-content" style="min-width: 450px;">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Actualizar reserva</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <input type="hidden" id="idreserva">
                            <div class="form-group">
                                <label>Fecha y hora</label>
                                <input type="text"  title="Seleccione una fecha" id="fechaUpdate" required="true" class="form-control"/>  
                            </div>  
                            <div class="form-group">
                                <label>Locación</label>
                                <select id="newLocationUpdate" class="form-control select">
                                    <option value="0">Seleccione una locación</option>
                                    <logic:iterate id="locacion" name="locaciones">
                                        <option value="<bean:write name="locacion" property="idlocacion"/>"><bean:write name="locacion" property="nombre"/></option>
                                    </logic:iterate>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Persona</label>
                                <select id="newUserUpdate" class="form-control select">
                                    <option value="0">Seleccione una persona</option>
                                    <logic:iterate id="usuario" name="usuarios">
                                        <option value="<bean:write name="usuario" property="idusuario"/>"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/> (<bean:write name="usuario" property="correo"/>)</option>
                                    </logic:iterate>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" id="aprobarEventBtn">Aprobar</button>
                        <button type="button" class="btn btn-main" id="updateEventBtn">Actualizar</button>
                        <button type="button" class="btn btn-danger" id="removeEventBtn">Eliminar</button>
                        <button type="button" class="btn btn-gray" data-dismiss="modal">Cerrar</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->



        <!-- End Modal -->
    </div>
    <!-- /.wrapper -->

    <!-- Jquery -->
    <script src="js/jquery-1.11.2.min.js"></script>

    <!-- Bootstrap -->
    <script src="bootstrap/bootstrap.min.js"></script>

    <!-- Modernizr -->
    <script src="js/modernizr.min.js"></script>

    <!-- SlimScroll -->
    <script src="js/jquery.slimscroll.min.js"></script>

    <!-- Animsition -->
    <script src="js/jquery.animsition.min.js"></script>

    <!-- Moment -->
    <script src="js/moment.min.js"></script>

    <!-- Full Calendar -->
    <script src="js/fullcalendar.min.js"></script>
    <!-- Full Calendar -->
    <script src="js/locale-all.js"></script>

    <!-- App -->
    <script src="js/app.min.js"></script>

    <script src="js-in/reservas.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>

</body>
</html>