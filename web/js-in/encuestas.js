/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tablePreguntas;

function init(grid) {
    loadView(grid);
    $(".select").select2();
}
$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#foto").attr("required", "true");
    $("#gestion").prop("accion", "crear");
    if (tablePreguntas) {
        tablePreguntas.clear();
        tablePreguntas.destroy();
    }
    tablePreguntas = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        columns: [
            {title: "Pregunta"},
            {title: "Tipo"},
            {title: "Opciones"},
            {title: "Gestión"}
        ]
    });
});
var id;
function eliminarEncuesta(idEncuesta) {
    id = idEncuesta;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarEncuestas.appropiate",
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
        var encuesta = grid[i];
        var result = "<div class=\"row\"> " +
                "    <div class=\"col-md-12 col-lg-12\">   " +
                "        <div class=\"search-result-items m-t-20\">" +
                "            <div class=\"search-result-item\">" +
                "                <div class=\"search-item-thumbnail\"><img src=\"img/" + encuesta.imagen + "\" alt=\"\" class=\"img-responsive\"></div>" +
                "                <div class=\"search-item-detail\">" +
                "                    <a href=\"#\" class=\"search-item-heading font-header\">" + encuesta.nombre + "</a>" +
                "                    <p class=\"search-item-description font-12\">Preguntas: " + encuesta.preguntas + "</p>" +
                "                    <div class=\"row m-t-15\">" +
                "                        <div class=\"col-sm-5\">" +
                "                            <div class=\"font-semi-bold \">" +
                "                               Numero de resultados parciales: <span class=\"text-main\">3</span> " +
                "                            </div>" +
                "                        </div><!-- /.col -->" +
                "                        <div class=\"col-sm-7 text-right text-left-xs\"> " +
                "                        </div><!-- /.col -->" +
                "                    </div><!-- /.row -->" +
                "                    <div class=\"row\">" +
                "                        <div class=\"col-sm-5\">" +
                "                            <div class=\"font-semi-bold \">" +
                "                                 Numero de resultados completos: <span class=\"text-main\">3</span>" +
                "                            </div>" +
                "                        </div><!-- /.col -->" +
                "                        <div class=\"col-sm-7 text-right text-left-xs\">" +
                "                            <button type=\"button\" class=\"btn btn-gray btn-icon\" onclick=\"eliminarEncuesta(" + encuesta.id + ")\"><i class=\"icon-bin\"></i></button>" +
                "                            <button type=\"button\" class=\"btn btn-dark\" onclick=\"LoadEncuestaJson(" + encuesta.id + ")\">Editar</button>" +
                "                            <button type=\"button\" class=\"btn btn-main\" onclick=\"LoadChat(" + encuesta.id + ")\">Comentarios</button>" +
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
function agregar() {
    var pregunta = $("#pregunta").val();
    var tipo = $("#tipo").val();
    var escala = $("#escala").val();
    if (tipo == 0) {
        alert("Seleccione un tipo");
        return;
    } else if (tipo == 3 && escala == '') {
        alert("ingrese una escala valida");
        return;
    }
    var tipoString = "";
    if (tipo == 1) {
        tipoString = "Si/No";
    } else if (tipo == 2) {
        tipoString = "Abierta";
    } else if (tipo == 3) {
        tipoString = "Escala";
    } else if (tipo == 4) {
        tipoString = "Multiple";
    }

    tablePreguntas.row.add([
        pregunta,
        tipoString,
        escala,
        "<i class=\"fa fa-remove fa-2x\" title=\"Eliminar pregunta\"></i>"
    ]).draw(false);

}
$('#datatable').on('click', '.fa-remove', function () {
    tablePreguntas
            .row($(this).parents('tr'))
            .remove()
            .draw();
});
$("#gestion").submit(function (e) {
    e.preventDefault();
    var data = tablePreguntas.data().toArray();
    $("#camposJson").val(JSON.stringify(data));

    var selected = [];
    $('#idpara :selected').each(function (i, sel) {
        selected.push($(sel).val());
    });
    $("#idparaString").val(JSON.stringify(selected));

    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearEncuestas.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarEncuesta.appropiate';
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
                init(json.grid);
                $('#gestion').trigger("reset");
            }
        }
    });
});

function LoadEncuestaJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#foto").removeAttr("required");

    $.ajax({
        url: "LoadEncuestaJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idencuesta").val(json.id);
        $("#nombre").val(json.nombre);
        $("#img").attr("src", json.img);

        if (tablePreguntas) {
            tablePreguntas.clear();
            tablePreguntas.destroy();
        }
        tablePreguntas = $('#datatable').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
            },
            data: json.grid,
            columns: [
                {title: "Pregunta"},
                {title: "Tipo"},
                {title: "Escala"},
                {title: "Gestión"}
            ]
        });
        //cargamos los paras
        var paras = json.paras;
        $('#idpara').select2("val", paras);
        $("#gestion-modal").modal("show");
    });
}
var idEncuesta;
function LoadChat(id) {
    idEncuesta = id;
    $.ajax({
        url: "LoadComentarios.appropiate",
        data: {
            id: id,
            tipo: 1
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
            id: idEncuesta,
            tipo: 1
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