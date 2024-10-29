/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    $(".select").select2();
}

function search() {
    var select = $("#select").val();
    $.ajax({
        url: "LoadResultadoDetalle.appropiate",
        data: {
            id: select
        }
    }).done(function (json) {
        $("#tbody").empty();
        for (var i = 0; i < json.preguntas.length; i++) {
            var pregunta = json.preguntas[i];
            var preguntaHtml = " <tr>" +
                    "    <td>"+pregunta.pregunta+"</td>" +
                    "    <td class=\"text-success text-center\">"+pregunta.tipo+"</td>" +
                    "    <td class=\"text-center\">"+pregunta.respuestas+"</td>" +
                    "</tr>";
            $("#tbody").append(preguntaHtml); 
        }
        
        $("#invitados").val(json.invitados);
        $("#parciales").val(json.parciales);
        $("#respuestas").val(json.respuestas);
        
    });
}
