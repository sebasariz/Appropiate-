/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tableCampos;
var id;
function init(grid) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre de la planilla"},
            {title: "Fecha de creación"},
            {title: "Fecha ultima actualización"},
            {title: "Fecha ultimo uso"},
            {title: "Gestión"}
        ]
    });
    $(".select").select2();
}
function LoadPlantillaJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");

    $.ajax({
        url: "LoadPlantillaJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idusuario").val(json.idusuario);
        $("#idTipoPlantilla").val(json.idTipoPlantilla).change();
        $("#idEntidad").val(json.idEntidad).change();
        $("#nombre").val(json.nombre);
        $("#apellidos").val(json.apellidos);
        $("#correo").val(json.email);
        $("#pass").val(json.pass);

        if ($("#idTipoPlantilla").val() == 1) {
            $("#entidad").hide();
        } else {
            $("#entidad").show();
        }

        $("#gestion-modal").modal("show");
    });
}
$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
    if (tableCampos) {
        tableCampos.clear();
        tableCampos.destroy();
    }
    tableCampos = $('#datatablecampos').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        columns: [
            {title: "Nombre Variable $variable"},
            {title: "Columna XLS Col A = 0"},
            {title: "Gestión"}
        ]
    });
});

function agregar() {
    var variable = $("#variable").val();
    var xls = $("#xls").val();
    tableCampos.row.add([
        variable,
        xls,
        "<i class=\"fa fa-remove fa-2x\" title=\"Eliminar pregunta\"></i>"
    ]).draw(false);

}
$('#datatablecampos').on('click', '.fa-remove', function () {
    tableCampos
            .row($(this).parents('tr'))
            .remove()
            .draw();
});

function EliminarPlantilla(idPlantilla) {
    id = idPlantilla;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}

function confirmarEliminar() {
    $.ajax({
        url: "EliminarPlantilla.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.grid.length > 0) {
                $('#datatable').dataTable().fnAddData(json.grid);
            }

        }
    });
}

$("#gestion").submit(function (e) {
    e.preventDefault();
    var data = tableCampos.data().toArray();
    $("#campos").val(JSON.stringify(data));
    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearPlantilla.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarPlantilla.appropiate';
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

function LoadPlantillaJson(id) {
    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#foto").removeAttr("required");

    $.ajax({
        url: "LoadPlantillaJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idfacturaTemplate").val(json.id);
        $("#nombre").val(json.nombre);
        $("#template").val(json.template);

        if (tableCampos) {
            tableCampos.clear();
            tableCampos.destroy();
        }
        tableCampos = $('#datatablecampos').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
            },
            data: json.grid,
            columns: [
                {title: "Nombre Variable $variable"},
                {title: "Columna XLS Col A = 0"},
                {title: "Gestión"}
            ]
        });

        $("#gestion-modal").modal("show");
    });
}
function PrevisualizarPlantilla(id) { 
    $.ajax({
        url: "LoadPlantillaJson.appropiate",
        data: {id: id}
    }).done(function (json) { 
        $("#html").append(json.template); 
        $("#preview-modal").modal("show");
    }); 
}
if ($("#idTipoPlantilla").val() == 1) {
    $("#entidad").hide();
} else {
    $("#entidad").show();
}
$("#idTipoPlantilla").on("change", function () {
    if (this.value == 1) {
        $("#entidad").hide();
    } else {
        $("#entidad").show();
    }
});
