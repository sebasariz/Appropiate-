
<!DOCTYPE html>
<html lang="en"> 
    <body> 
        <div class="page-header font-header">Actividades</div>
        <ol class="breadcrumb">
            <li><a href="#">Inicio</a></li>
            <li><a href="#">Actividades</a></li> 
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
                        <h4 class="modal-title">Agregar actividad</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>Fecha y hora</label>
                                <input type="text"  title="Seleccione una fecha" id="fecha" required="true" class="form-control"/>  
                            </div> 
                            <div class="form-group">
                                <label>Actividad</label>
                                <textarea type="text"  title="Ingrese la actividad" id="actividad" required="true" class="form-control"> </textarea> 
                            </div> 
                            <div class="form-group">
                                <label>Responsable</label>
                                <input type="text"  title="Responsable" id="responsable" required="true" class="form-control"/>  
                            </div>
                            <div class="form-group">
                                <label>Correo electronico</label>
                                <input type="text"  title="Correo electronico de notificación" id="correo"
                                       required="true" class="form-control"/>  
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
                        <h4 class="modal-title">Actualizar actividad</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <input type="hidden" id="idactividad">
                            <div class="form-group">
                                <label>Fecha y hora</label>
                                <input type="text"  title="Seleccione una fecha" id="fechaUpdate" required="true" class="form-control"/>  
                            </div> 
                            <div class="form-group">
                                <label>Actividad</label>
                                <textarea type="text"  title="Ingrese la actividad" id="actividadUpdate" required="true" class="form-control"> </textarea> 
                            </div> 
                            <div class="form-group">
                                <label>Responsable</label>
                                <input type="text"  title="Responsable" id="responsableUpdate" required="true" class="form-control"/>  
                            </div>
                            <div class="form-group">
                                <label>Correo electronico</label>
                                <input type="text"  title="Correo electronico de notificación" id="correoUpdate"
                                       required="true" class="form-control"/>  
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer"> 
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

    <script src="js-in/actividades.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("grid")%>);
        });
    </script>

</body>
</html>