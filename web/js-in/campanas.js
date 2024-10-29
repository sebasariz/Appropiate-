/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init(grid) {
    loadView(grid);
    $('#informacionEditor').summernote();
    $(".select").select2();
    $('#fecha').datetimepicker();
}
$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#foto").attr("required", "true");
    $("#gestion").prop("accion", "crear");

});
var id;
function eliminarCampana(idCampana) {
    id = idCampana;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarCampana.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            loadView(json.grid);
        }
    });
}
function loadView(grid) {
    $("#result").empty();
    for (var i = 0; i < grid.length; i++) {
        var campana = grid[i];
        var result = "<div class=\"row\"> " +
                "    <div class=\"col-md-12 col-lg-12\">   " +
                "        <div class=\"search-result-items m-t-20\">" +
                "            <div class=\"search-result-item\">" +
                "                <div class=\"search-item-detail\">" +
                "                    <a href=\"#\" class=\"search-item-heading font-header\">" + campana.nombre + " ( " + campana.fecha + " )</a>" +
                "                    <p class=\"search-item-description font-12\">" + campana.informacion + "</p>" +
                "                    <div class=\"row m-t-15\">" +
                "                        <div class=\"col-sm-5\">" +
                "                            <div class=\"font-semi-bold \">" +
                "                               Numero de aperturas: <span class=\"text-main\">" + campana.aperturas + "</span> " +
                "                            </div>" +
                "                        </div><!-- /.col -->" +
                "                        <div class=\"col-sm-7 text-right text-left-xs\"> " +
                "                        </div><!-- /.col -->" +
                "                    </div><!-- /.row -->" +
                "                    <div class=\"row\">" +
                "                        <div class=\"col-sm-5\">" +
                "                            <div class=\"font-semi-bold \">" +
                "                                 Numero de archivos: <span class=\"text-main\">" + campana.archivos + "</span>" +
                "                            </div>" +
                "                        </div><!-- /.col -->" +
                "                        <div class=\"col-sm-7 text-right text-left-xs\">" +
                "                            <button type=\"button\" class=\"btn btn-gray btn-icon\" onclick=\"eliminarCampana(" + campana.id + ")\"><i class=\"icon-bin\"></i></button>" +
                "                            <button type=\"button\" class=\"btn btn-dark\" onclick=\"LoadCampanaJson(" + campana.id + ")\">Editar</button>" +
                "                            <button type=\"button\" class=\"btn btn-main\" onclick=\"LoadChat(" + campana.id + ")\">Comentarios</button>" +
                "                        </div><!-- /.col -->" +
                "                    </div><!-- /.row -->" +
                "                </div>" +
                "            </div><!-- /.serach-item -->" +
                "        </div><!-- /.serach-items --> " +
                "    </div><!-- /.col -->" +
                "</div><!-- /.row -->";
        $("#result").append(result);
    }

}


$("#gestion").submit(function (e) {
    e.preventDefault();

    var date = $('#fecha').data("DateTimePicker").date();
    var dateString = new Date(date);
    $("#fechaHidden").val(dateString.getTime());

    var selected = [];
    $('#idpara :selected').each(function (i, sel) {
        selected.push($(sel).val());
    });
    $("#idparaString").val(JSON.stringify(selected));

    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearCampana.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarCampana.appropiate';
    }
    $.ajax({
        type: "POST",
        cache: false,
        url: action,
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal").modal('hide');
                loadView(json.grid);
                $('#gestion').trigger("reset");
            }
        }
    });
});

function LoadCampanaJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#foto").removeAttr("required");

    $.ajax({
        url: "LoadCampanaJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idcampana").val(json.id);
        $("#nombre").val(json.nombre);
        $("#informacion").val(json.informacion);
        $('#fecha').data("DateTimePicker").date(new Date(json.fecha));
        $("#idpara").select2("val",json.paras);
        $("#gestion-modal").modal("show");
    });
}
var idCampana;
function LoadChat(id) {
    idCampana = id;
    $.ajax({
        url: "LoadComentarios.appropiate",
        data: {
            id: id,
            tipo: 4
        }
    }).done(function (json) {
        $("#comentarios").empty();
        for (var i = 0; i < json.grid.length; i++) {
            var comentario = json.grid[i];
            var date = new Date(comentario.fecha);
            var chat = "<div class=\"chat-item2 " + comentario.site + "\">"
                    + "    <div class=\"chat-content\">"
                    + "        <div class=\"chat-bubble\">" + comentario.comentario + "</div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + comentario.usuario + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + date.toLocaleDateString() + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "    </div>"
                    + "</div>";

            $("#comentarios").append(chat);
        }
        $("#comentarios-modal").modal("show");
    });
}

function saveComment() {
//    SaveComentario 
    $.ajax({
        url: "SaveComentario.appropiate",
        data: {
            comentario: $("#comentarioString").val(),
            id: idCampana,
            tipo: 4
        }
    }).done(function (json) {
        $("#comentarios").empty();
        for (var i = 0; i < json.grid.length; i++) {
            var comentario = json.grid[i];
            var date = new Date(comentario.fecha);
            var chat = "<div class=\"chat-item2 " + comentario.site + "\">"
                    + "    <div class=\"chat-content\">"
                    + "        <div class=\"chat-bubble\">" + comentario.comentario + "</div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + comentario.usuario + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + date.toLocaleDateString() + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "    </div>"
                    + "</div>";

            $("#comentarios").append(chat);
        }
        $("#comentarioString").val('');
    });
}
$("#comentarioString").keypress(function (e) {
    if (e.which == 13) {
        saveComment();
    }
});