/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var eventTitle;
var eventStart;
var selectedEvent;

function init(grid) {

    loadView(grid);
    initEditor();

    $(".select").select2();
    $('#fecha').datetimepicker({date: new Date()});
    $("#fechaUpdate").datetimepicker({date: new Date()});
}

function loadView(grid) {
//    $("#actividades").empty();
    //cargamos las actividades
    for (var i = 0; i < grid.length; i++) {
        var json = grid[i];
        var add = "<li class=\"online \" onclick=\"LoadChat(" + json.id + ",'" + json.actividad + "')\">" +
                "    <div class=\"profile-pic\">" +
                "        <img src=\"img/tarea.png\" class=\"img-circle\" alt=\"\">" +
                "    </div>" +
                "    <div class=\"chat-heading\">" +
                "        <span class=\"chat-time\">" + json.fecha + "</span>" +
                "        <div class=\"chat-name font-semi-bold font-12\">" + json.actividad + "</div>" +
                "    </div>" +
                "    <div class=\"chat-preview\">" +
                "        <span class=\"badge badge-danger\"></span>" +
                "        <p class=\"font-12\">" + json.responsable + "</p>" +
                "    </div>" +
                "</li>";
        $("#actividades").append(add);
    }
}

function initEditor() {
    $('.chat-wrapper .chat-friend-list > li:not(.divider)').click(function () {
        //Loading Content Demo
        if (!$('.chat-content').hasClass('loading')) {
            $('.chat-content').addClass('loading');

            setTimeout(function () {
                $('.chat-content').removeClass('loading');
            }, 2000);
        }

        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        //For small device
        if (Modernizr.mq('(max-width: 767px)')) {
            $('.app-wrapper').addClass('content-opened');
        }
    });

    //Back to friend list on small device
    $('.chat-back').click(function () {
        $('.app-wrapper').removeClass('content-opened');
    });


    var height = $(document).height() - 55;
    $(".main-container").height(height);
}

var idActividad;
function LoadChat(id, actividad) {
    $("#act").text(actividad);
    idActividad = id;
    $.ajax({
        url: "LoadComentarios.appropiate",
        data: {
            id: id,
            tipo: 5
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
            id: idActividad,
            tipo: 5
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

 