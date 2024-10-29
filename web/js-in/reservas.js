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
    $('#fecha').datetimepicker({date: new Date()});
    $("#fechaUpdate").datetimepicker({date: new Date()});
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
        editable: true,
        select: function (start, end) {
            eventStart = start;
            $('#fecha').data("DateTimePicker").date(start);
            $('#addEventModal').modal('show');
        },
        eventClick: function (event, element) {
            selectedEvent = event;
            $('#idreserva').val(selectedEvent.id);
            $('#fechaUpdate').data("DateTimePicker").date(selectedEvent.start);
            $('#newLocationUpdate').val(selectedEvent.idLocacion).trigger('change');
            $('#newUserUpdate').val(selectedEvent.idUsuario).trigger('change');
            ;

            $('#updateEventModal').modal('show');
        },
        eventLimit: true, // allow "more" link when too many events
        events: grid
    });
}



function addEvent(locacion, usuario, title, start) {
    
//aqui agregamos el evento y la reserva a base de datos y al calendario
    $.ajax({
        url: "CrearReserva.appropiate",
        data: {
            locacion: locacion,
            usuario: usuario,
            start: start.getTime()
        }
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            var eventData = {
                id: json.id,
                idUsuario: usuario,
                idLocacion: locacion,
                title: title,
                start: start
            };
            $('#fullCalendar').fullCalendar('renderEvent', eventData, true); // stick? = true 
            $('#fullCalendar').fullCalendar('unselect');
            $('#addEventModal').modal('hide');
        }
    });
}

function updateEvent(locacion, usuario, title, start) {
   

    selectedEvent.idLocacion = locacion;
    selectedEvent.idUsuario = usuario;
    selectedEvent.start = start;
    selectedEvent.title = title;


    $.ajax({
        url: "EditarReserva.appropiate",
        data: {
            id: selectedEvent.id,
            locacion: locacion,
            usuario: usuario,
            start: utc_timestamp
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



//Add event Modal  
$('#addEventBtn').click(function () {

    var newLocation = $("#newLocation").val();
    var newUser = $("#newUser").val();

    if (newLocation == 0) {
        $("#errorLabel").text("Seleccione una locación.");
        $("#modal-error").modal("show");
        return;
    }
    if (newUser == 0) {
        $("#errorLabel").text("Seleccione un usaurio.");
        $("#modal-error").modal("show");
        return;
    }
    var newLocationText = $("#newLocation").children("option").filter(":selected").text();
    var newUserText = $("#newUser").children("option").filter(":selected").text();
    var eventTitle = newLocationText + "(" + newUserText + ")";
    var date = $('#fecha').data("DateTimePicker").date();
    var eventStart = new Date(date);


    addEvent(newLocation, newUser, eventTitle, eventStart);
});

//Update event Modal
$('#updateEventBtn').click(function () {

    var newLocation = $("#newLocationUpdate").val();
    var newUser = $("#newUserUpdate").val();

    if (newLocation == 0) {
        $("#errorLabel").text("Seleccione una locación.");
        $("#modal-error").modal("show");
        return;
    }
    if (newUser == 0) {
        $("#errorLabel").text("Seleccione un usaurio.");
        $("#modal-error").modal("show");
        return;
    }
    var newLocationText = $("#newLocationUpdate").children("option").filter(":selected").text();
    var newUserText = $("#newUserUpdate").children("option").filter(":selected").text();
    var eventTitle = newLocationText + "(" + newUserText + ")";
    var date = $('#fechaUpdate').data("DateTimePicker").date();
    var eventStart = new Date(date);

    updateEvent(newLocation, newUser, eventTitle, eventStart);


});

//Remove event from calendar
$('#removeEventBtn').click(function () {
    $.ajax({
        url: "EliminarReserva.appropiate",
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
    clearEventData();
});


$('#aprobarEventBtn').click(function () {
    $.ajax({
        url: "AprobarReserva.appropiate",
        data: {
            id: selectedEvent.id
        }
    }).done(function (json) { 
        $("#modal-esperar").modal('hide');
        if (json.error) { 
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $('#updateEventModal').modal('hide');
            $("#errorLabel").text("La reserva ha sido aprobada de forma exitosa.");
            $("#modal-error").modal("show");
        }
    });

});