/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function init() {


}

function search() {
    $.ajax({
        url: "BuscarHistorial.appropiate",
        data: {
            campo: $("#campo").val()
        }
    }).done(function (json) { 
        var grid = json.grid;
        $("#result").empty();
        for (var i = 0; i < grid.length; i++) {
            var pqr = grid[i]; 
            var pqrHtml = "<div class=\"row\">"
                    + "<div class=\"col-md-12 col-lg-12\">"
                    + "<div class=\"search-result-items m-t-20\">"
                    + "<div class=\"search-result-item\">"
                    + "    <div class=\"search-item-detail\">"
                    + "        <a href=\"#\" class=\"search-item-heading font-header\">"+pqr.usuario+"</a>"
                    + "        <div class=\"text-muted font-12\">Fecha creación:"+pqr.fecha+"</div>"
                    + "        <div class=\"text-muted font-12\">"+pqr.estado+"</div>"
                    + "        <p class=\"search-item-description font-12\">"+pqr.solicitud+"</p>"
                    + "        <div class=\"row\">"
                    + "            <div class=\"col-sm-5\">"
                    + "                <div class=\"font-semi-bold \">"
                    + "                    Tipo solicitud: <span class=\"text-main\">"+pqr.tipo+"</span>"
                    + "                </div>"
                    + "            </div><!-- /.col -->"
                    + "            <div class=\"col-sm-7 text-right text-left-xs\"> "
                    + "                <button type=\"button\" class=\"btn btn-main\" onclick=\"LoadChat("+pqr.id+")\">Comentarios</button>"
                    + "            </div><!-- /.col -->"
                    + "        </div><!-- /.row -->"
                    + "    </div>"
                    + "</div><!-- /.serach-item -->"
                    + "   </div><!-- /.serach-items --> "
                    + "</div><!-- /.col -->"
                    + "</div><!-- /.row -->";
            $("#result").append(pqrHtml);
        }
    });
}
 
var idEncuesta;
function LoadChat(id) { 
    idEncuesta = id;
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
 