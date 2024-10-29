/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
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
            {title: "Tipo usuario"},
            {title: "Entidad"},
            {title: "Gestión"}
        ]
    });

    var table = $('#datatable-grupos').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: ""},
        ]
    });
    $(".select").select2();
}
function LoadUsuarioJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");

    $.ajax({
        url: "LoadUsuarioJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idusuario").val(json.idusuario);
        $("#idTipoUsuario").val(json.idTipoUsuario).change();
        $("#idEntidad").val(json.idEntidad).change();
        $("#nombre").val(json.nombre);
        $("#apellidos").val(json.apellidos);
        $("#correo").val(json.email);
        $("#pass").val(json.pass);

        if ($("#idTipoUsuario").val() == 1) {
            $("#entidad").hide();
        } else {
            $("#entidad").show();
        }

        $("#gestion-modal").modal("show");
    });
}

function LoadGruposDeInteresJson(idUsuario) {
    id = idUsuario;
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "LoadGruposDeInteresJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        $('#datatable-grupos').dataTable().fnClearTable();
        $('#datatable-grupos').dataTable().fnAddData(json.grid);
        $("#gestion-modal-grupos").modal("show");
    });
}
function slect(select, idGrupo) {
    $.ajax({
        url: "SaveGrupoUsuario.appropiate",
        data: {
            id: idGrupo,
            state: select.checked,
            idUsuario: id
        }
    }); 
}


$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
});
function EliminarUsuario(idUsuario) {
    id = idUsuario;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $.ajax({
        url: "EliminarUsuario.appropiate",
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

    var selected = [];
    $('#idEntidad :selected').each(function (i, sel) {
        selected.push($(sel).val());
    });
    $("#idEntidadString").val(JSON.stringify(selected));

    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearUsuario.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarUsuario.appropiate';
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
                e.reset();
            }
        }
    });
});
if ($("#idTipoUsuario").val() == 1) {
    $("#entidad").hide();
} else {
    $("#entidad").show();
}
$("#idTipoUsuario").on("change", function () {
    if (this.value == 1) {
        $("#entidad").hide();
    } else {
        $("#entidad").show();
    }
});
