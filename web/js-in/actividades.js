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
    $(".select").select2();
    $('#fecha').datetimepicker({ defaultDate: new Date()});
    $("#fechaUpdate").datetimepicker({defaultDate: moment()});
}

function loadView(grid) {
    $('#fullCalendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,basicWeek,basicDay'
        },
        locale: 'es',
        defaultDate: new Date(),
        editable: true,
        businessHours: true, // display business hours
        selectable: true,
        selectHelper: true, 
        select: function (start, end) {
            eventStart = start; 
            var today = new Date();
            var date = new Date(start);
            date.setDate(date.getDate()+1);
            date.setHours(today.getHours());
            date.setMinutes(today.getMinutes());
            $('#fecha').data("DateTimePicker").date(date);
            $('#addEventModal').modal('show');
        },
        eventClick: function (event, element) {
            selectedEvent = event;
            $('#idactividad').val(selectedEvent.id);
            $('#fechaUpdate').data("DateTimePicker").date(selectedEvent.start);
            $("#actividadUpdate").val(selectedEvent.actividad);
            $("#responsableUpdate").val(selectedEvent.responsable);
            $("#correoUpdate").val(selectedEvent.correo);

            $('#updateEventModal').modal('show');
        },
        eventLimit: true, // allow "more" link when too many events
        events: grid
    });
}


//Add event Modal  
$('#addEventBtn').click(function () { 
    var fecha = $('#fecha').data("DateTimePicker").date();
    var actividad = $("#actividad").val();
    var responsable = $("#responsable").val();
    var correo = $("#correo").val();
    var eventStart = new Date(fecha);
    addEvent(eventStart, actividad, responsable, correo);
});

function addEvent(fecha, actividad, responsable, correo) {
    $.ajax({
        url: "CrearActividad.appropiate",
        data: {
            fecha: fecha.getTime(),
            correo: correo,
            responsable: responsable,
            actividad: actividad
        }
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            var eventData = {
                id: json.id,
                correo: correo,
                responsable: responsable,
                actividad: actividad,
                title: actividad + " (" + responsable + ")",
                start: fecha
            };
            $('#fullCalendar').fullCalendar('renderEvent', eventData, true); // stick? = true 
            $('#fullCalendar').fullCalendar('unselect');
            $('#addEventModal').modal('hide');
        }
    });
}

//Update event Modal
$('#updateEventBtn').click(function () {
    var fecha = $('#fechaUpdate').data("DateTimePicker").date();
    var actividad = $("#actividadUpdate").val();
    var responsable = $("#responsableUpdate").val();
    var correo = $("#correoUpdate").val();
    var fechaInit = new Date(fecha);
    updateEvent(fechaInit, actividad, responsable, correo);


});
function updateEvent(fecha, actividad, responsable, correo) {

    selectedEvent.responsable = responsable;
    selectedEvent.actividad = actividad;
    selectedEvent.correo = correo;
    selectedEvent.fecha = fecha;
    selectedEvent.title = actividad + " (" + responsable + ")";

    $.ajax({
        url: "EditarActividad.appropiate",
        data: {
            id: selectedEvent.id,
            correo: correo,
            responsable: responsable,
            actividad: actividad,
            fecha: fecha.getTime()
        }
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $('#fullCalendar').fullCalendar('updateEvent', selectedEvent);
            $('#fullCalendar').fullCalendar('unselect');
            $('#updateEventModal').modal('hide');
        }
    });
}

 

//Remove event from calendar
$('#removeEventBtn').click(function () {
    $.ajax({
        url: "EliminarActividad.appropiate",
        data: {
            id: selectedEvent.id
        }
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $('#fullCalendar').fullCalendar('removeEvents', selectedEvent.id);
            $('#updateEventModal').modal('hide');
        }
    });
});

$('#addEventModal').on('hidden.bs.modal', function (e) {

});


 