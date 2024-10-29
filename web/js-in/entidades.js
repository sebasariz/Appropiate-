/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function init(grid) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: "Contacto"},
            {title: "Dirección"},
            {title: "Telefono"},
            {title: "Nit"},
            {title: "Identificador"},
            {title: "Gestión"}
        ]
    });
    $(".select").select2();
}

function LoadEntidadJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");

    $.ajax({
        url: "LoadEntidadJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#identidad").val(json.id);
        $("#nombre").val(json.nombre);
        $("#contacto").val(json.contacto);
        $("#direccion").val(json.direccion);
        $("#telefono").val(json.telefono);
        $("#correo").val(json.correo);
        $("#identificador").val(json.identificador);
        $("#nit").val(json.nit);
        $("#reservas").val(json.reservas);
        $("#pasarela").val(json.facturacion);
        $("#camposPasarela").val(json.camposPasarela); 
        $("#gestion-modal").modal("show");
    });
}

$("#add").on("click", function () {
    var uuid = guid();
    $("#identificador").val(uuid);
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
});

var id;
function EliminarEntidad(idUsuario) {
    id = idUsuario;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarEntidad.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            $('#datatable').dataTable().fnAddData(json.grid);

        }
    });
}
$("#gestion").submit(function (e) {
    e.preventDefault();
    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = ""; 
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearEntidad.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarEntidad.appropiate';
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
                $('#datatable').dataTable().fnClearTable();
                $('#datatable').dataTable().fnAddData(json.grid);
                $('#gestion').trigger("reset"); 
            }
        }
    });
});
function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
}
//entidad
