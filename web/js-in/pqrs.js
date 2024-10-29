/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tablePreguntas;

function init(grid) {

    var height = $(document).height() - 55;
    $(".main-container").height(height);

    loadPQRS(grid);

    $(".select").select2();
}

function loadPQRS(pqrs) {
    $("#pqrs").empty();
    for (var i = 0; i < pqrs.length; i++) {
        var pqr = pqrs[i];
        var pqrsItem = "<li onclick=\"LoadChat(" + pqr.id + ",'" + pqr.usuario + "')\"> " +
                "<div class=\"chat-heading\">" +
                "    <span class=\"chat-time\">" + pqr.fecha + "(" + pqr.tipo + ")</span>" +
                "    <div class=\"chat-name font-semi-bold font-12\">" + pqr.usuario + "</div>" +
                "</div>" +
                "<div class=\"chat-preview\" onclick=\"LoadChat(" + pqr.id + ",'" + pqr.usuario + "')\"> " +
                "    <p class=\"font-12\">" + pqr.solicitud + "</p>" +
                "</div>" +
                "</li>";
        $("#pqrs").append(pqrsItem);
    }
}

var idPqr;
function LoadChat(id, nombre) {
    idPqr = id;
    $('.chat-content').addClass('loading');
    $("#nombre").empty().append(nombre);
    $.ajax({
        url: "LoadComentarios.appropiate", 
        data: {
            id: id,
            tipo: 2
        }
    }).done(function (json) {
        $("#comentarios").empty();
        for (var i = 0; i < json.grid.length; i++) {
            var comentario = json.grid[i];
            var date = new Date(comentario.fecha);
            var chat = "<div class=\"chat-item2 " + comentario.site + "\">"
                    + "    <div class=\"chat-content\">"
                    + "        <div class=\"chat-bubble\">" + decodeURIComponent(comentario.comentario) + "</div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + comentario.usuario + " </div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + date.toLocaleDateString() + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "    </div>"
                    + "</div>";

            $("#comentarios").append(chat);
        }
        //For small device
//        alert("size: "+Modernizr.mq('(max-width: 967px)'))
        if (Modernizr.mq('(max-width: 767px)')) {
            $('.app-wrapper').addClass('content-opened');
        }
        $("#comentarios-modal").modal("show");
        $('.chat-content').removeClass('loading');
    });
}

function saveComment() {
//    SaveComentario 
    $('.chat-content').addClass('loading');
    $.ajax({
        url: "SaveComentario.appropiate",
        data: {
            comentario: $("#comentarioString").val(),
            id: idPqr,
            tipo: 2
        }
    }).done(function (json) {
        $("#comentarios").empty();
        for (var i = 0; i < json.grid.length; i++) {
            var comentario = json.grid[i];
            var date = new Date(comentario.fecha);
            var chat = "<div class=\"chat-item2 " + comentario.site + "\">"
                    + "    <div class=\"chat-content\">"
                    + "        <div class=\"chat-bubble\">" + comentario.comentario + "</div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + comentario.usuario + "</div>"
                    + "        <div class=\"font-10 text-muted chat-sent\">" + date.toLocaleDateString() + " <i class=\"icon-checkmark text-success m-d-1\"></i></div>"
                    + "    </div>"
                    + "</div>";

            $("#comentarios").append(chat);
        }
        $("#comentarioString").val('');
        $('.chat-content').removeClass('loading');
    });
}

function finalizarPQRS() {
    $("#cerrar-modal").modal("show");
}

function confirmarFinalizar() {
    $.ajax({
        url: "FinalizarSolicitud.appropiate",
        data: {
            id: idPqr
        }
    }).done(function (json) {
        loadPQRS(json.grid);
    });
}


//Back to friend list on small device
$('.chat-back').click(function () {
    $('.app-wrapper').removeClass('content-opened');
});



 