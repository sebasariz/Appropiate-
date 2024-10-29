/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tableModulos;

function init(grid) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: "Apellidos"},
            {title: "Correo"},
            {title: "Modulos"}
        ]
    });
    $(".select").select2();
}
var idUsuario;
function LoadSubmodulosUsuario(id) {
    idUsuario = id;
    $.ajax({
        url: "LoadSubmodulosUsuario.appropiate",
        data: {idUsuario: id}
    }).done(function (json) {
        if (tableModulos) {
            tableModulos.clear();
            tableModulos.destroy();
        }
        tableModulos = $('#datatable-modulos').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
            },
            data: json,
            columns: [
                {title: "Nombre"},
                {title: "Modulo"},
                {title: ""}
            ]
        });
        $("#gestion-modal").modal("show");


    });
}

function SaveSubmoduloUsuario(id, value) { 
    $.ajax({
        url: "SaveSubmoduloUsuario.appropiate",
        data: {
            idUsuario: idUsuario,
            idSubmodulo: id,
            state: value
        }
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {

        }
    });
}
