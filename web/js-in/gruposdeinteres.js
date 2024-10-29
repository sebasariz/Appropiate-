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
            {title: "Fecha"},
            {title: "Afiliados"},
            {title: "Gestión"}
        ]
    });
    $(".select").select2();
}

function LoadGrupoDeInteresJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");

    $.ajax({
        url: "LoadGrupoDeInteresJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idgrupo").val(json.id);
        $("#nombre").val(json.nombre);
        $("#gestion-modal").modal("show");
    });
}

$("#add").on("click", function () { 
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
});

var id;
function EliminarGrupoDeInteres(idGrupo) {
    id = idGrupo;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarGrupoDeInteres.appropiate",
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
        action = 'CrearGrupoDeInteres.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarGrupoDeInteres.appropiate';
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
