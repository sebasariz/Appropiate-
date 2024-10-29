/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tableConceptos;
var id;


function init(grid) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Usuario"},
            {title: "Referencia"},
            {title: "Fecha Emisión"},
            {title: "Valor a pagar"},
            {title: "Total pagado"},
            {title: "Estado"},
            {title: "Gestión"}
        ]
    });
    $(".select").select2();
    $('#fecha').datetimepicker({date: new Date()});
}
$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
    if (tableConceptos) {
        tableConceptos.clear();
        tableConceptos.destroy();
    }
    tableConceptos = $('#datatableConceptos').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        columns: [
            {title: "Item"},
            {title: "Unidades"},
            {title: "Valor"},
            {title: "Gestión"}
        ]
    });
});

function agregar() {
    var item = $("#item").val();
    var unidad = $("#unidades").val();
    var valor = $("#valor").val();
    tableConceptos.row.add([
        item,
        unidad,
        valor,
        "<i class=\"fa fa-remove fa-2x\" title=\"Eliminar pregunta\"></i>"
    ]).draw(false);

    //sumar al total
    var valoractual = parseFloat($("#valor").val());
    var unidades = parseFloat(unidad);

    var valorTotal = 0;
    if ($("#valortotal").val() != '') {
        valorTotal = parseFloat($("#valortotal").val());
    }

    valorTotal = valorTotal + (valoractual * unidades);
    $("#valortotal").val(valorTotal);


}
$('#datatablecampos').on('click', '.fa-remove', function () {
    tableConceptos
            .row($(this).parents('tr'))
            .remove()
            .draw();
});

function EliminarFactura(idFactura) {
    id = idFactura;
    $(".modal-title").text("Eliminar");
    $("#remove-modal").modal("show");
}

function confirmarEliminar() {
    $.ajax({
        url: "EliminarFactura.appropiate",
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
    var date = $('#fecha').data("DateTimePicker").date();
    var dateString = new Date(date);
    $("#fechaHidden").val(dateString.getTime());

    var data = tableConceptos.data().toArray();
    $("#conceptos").val(JSON.stringify(data));
    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CrearFactura.appropiate';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditarFactura.appropiate';
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

function LoadFacturaJson(id) {
    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $.ajax({
        url: "LoadFacturaJson.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#idfactura").val(json.id);
        $("#idUsuario").val(json.idUsuario).change();
        $("#referencia").val(json.referencia);
        $('#fecha').data("DateTimePicker").date(new Date(json.fecha));
        $("#idTemplate").val(json.idTemplate).change();
        $("#valortotal").val(json.valor);
        $("#referencia").val(json.referencia);
        if (tableConceptos) {
            tableConceptos.clear();
            tableConceptos.destroy();
        }
        tableConceptos = $('#datatableConceptos').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
            },
            data: json.grid,
            columns: [
                {title: "Item"},
                {title: "Unidades"},
                {title: "Valor"},
                {title: "Gestión"}
            ]
        });

        $("#gestion-modal").modal("show");
    });
}


function MarcarPaga(idFactura) {
    id = idFactura;
    $("#pagar-modal").modal("show");
}

function ConfirmarMarcarPaga() {
    $.ajax({
        url: "PagarFactura.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#pagar-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.grid.length > 0) {
                $('#datatable').dataTable().fnAddData(json.grid);
            }

        }
    });
}

function RecordarFactura(idFactura) {
    id = idFactura;
    $("#recordar-modal").modal("show");
}

function RecordatorioPago() {
    $.ajax({
        url: "RecordatorioFactura.appropiate",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#recordar-modal").modal('hide');
            $("#errorLabel").text("Recordatorio enviado de forma correcta.");
            $("#modal-error").modal("show");

        }
    });
}

$("#xls").on("click", function () {
    $("#gestion-modal").modal("show");
     
});