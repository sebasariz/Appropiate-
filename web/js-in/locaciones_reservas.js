/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init(grid) {
    loadView(grid);
//    $('#informacionEditor').summernote();
//    $(".select").select2();
//    $('#fecha').datetimepicker();
}
$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#foto").attr("required", "true");
    $("#img").removeAttr("src");
    $("#gestion").prop("accion", "crear");

});
var id;
function eliminarLocacion(idLocacion) {
    id = idLocacion;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarLocacion.appropiate",
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
        var locacion = grid[i];
        var result = "<div class=\"row\"> " +
                "    <div class=\"col-md-12 col-lg-12\">   " +
                "        <div class=\"search-result-items m-t-20\">" +
                "            <div class=\"search-result-item\">" +
                "                <div class=\"search-item-thumbnail\"><img src=\"img/" + locacion.imagen + "\" alt=\"\" class=\"img-responsive\"></div>" +
                "                <div class=\"search-item-detail\">" +
                "                    <a href=\"#\" class=\"search-item-heading font-header\">" + locacion.nombre + "</a>" +
                "                    <div class=\"text-muted font-12\">Responsable: " + locacion.responsable + "</div>" +
                "                    <p class=\"search-item-description font-12\">" + locacion.descripcion + "</p>" +
                "                    <div class=\"row\">" +
                "                        <div class=\"col-sm-5\">" +
                "                            <div class=\"font-semi-bold \">" +
                "                                 Reservas: <span class=\"text-main\">" + locacion.reservas + "</span>" +
                "                            </div>" +
                "                        </div><!-- /.col -->" +
                "                        <div class=\"col-sm-7 text-right text-left-xs\">" +
                "                            <button type=\"button\" class=\"btn btn-gray btn-icon\" onclick=\"eliminarLocacion(" + locacion.id + ")\"><i class=\"icon-bin\"></i></button>" +
                "                            <button type=\"button\" class=\"btn btn-dark\" onclick=\"LoadLocacionJson(" + locacion.id + ")\">Editar</button>" +
                "                            <button type=\"button\" class=\"btn btn-main\" onclick=\"LoadChat(" + locacion.id + ")\">Comentarios</button>" +
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

    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearLocacion.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarLocacion.appropiate';
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

function LoadLocacionJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#foto").removeAttr("required");

    $.ajax({
        url: "LoadLocacionJson.appropiate",
        data: {id: id}
    }).done(function (json) { 
        $("#idlocacion").val(json.id);
        $("#nombre").val(json.nombre);
        $("#img").attr("src", json.img);
        $("#descripcion").val(json.descripcion);
        $("#responsable").val(json.responsable);  
        $("#gestion-modal").modal("show");
    });
}
var idLocacion;
function LoadChat(id) {
    idLocacion = id;
    $.ajax({
        url: "LoadComentarios.appropiate",
        data: {
            id: id,
            tipo: 3
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
            id: idLocacion,
            tipo: 3
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