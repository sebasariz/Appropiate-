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
            {title: "Nombre Apellidos"},
            {title: "Fecha"},
            {title: "Acción"}
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
    $('#fecha').datetimepicker();
}



var mainColor = '#122b14';
var secondaryColor = '#34495e';

//graficas
function labelFormatter(label, series) {
    return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
}

function initChart(char_encuesta,char_evento) { 
    //grafico de enceusta  
    $.plot($('.line-placeholder'), char_encuesta, {
        colors: [mainColor, secondaryColor],
        series: {
            pie: {
                show: true,
                radius: 1,
                label: {
                    show: true,
                    radius: 3 / 4,
                    formatter: labelFormatter,
                    background: {
                        opacity: 0.5
                    }
                }
            }
        },
        legend: {
            show: false
        }
    });
    //grafico de evento 
//    alert("chart: "+JSON.stringify(char_evento));
    $.plot($('.line-placeholder-2'), char_evento, {
        colors: [mainColor, secondaryColor],
        series: {
            pie: {
                show: true,
                radius: 1,
                label: {
                    show: true,
                    radius: 3 / 4,
                    formatter: labelFormatter,
                    background: {
                        opacity: 0.5
                    }
                }
            }
        },
        legend: {
            show: false
        }
    });
    //grafico de comentarios
    var data = [{data: [[20, 5], [21, 8], [22, 5], [23, 8], [24, 7], [25, 9], [26, 8], [27, 8], [28, 10], [29, 12], [30, 10], [31, 5], [32, 9]],
            label: "Comentarios"
        }],
            options = {
                xaxis: {
                    ticks: [[21, 'Jan'], [22, 'Feb'], [23, 'Mar'], [24, 'Apr'], [25, 'May'], [26, 'Jun'], [27, 'Jul'], [28, 'Aug'], [29, 'Sep'], [30, 'Oct'], [31, 'Nov'], [32, 'Dec']]
                },
                series: {
                    lines: {
                        show: true,
                    },
                    points: {
                        show: true,
                        radius: '3.5'
                    },
                    shadowSize: 0
                },
                grid: {
                    hoverable: true,
                    clickable: true,
                    color: '#bbb',
                    borderWidth: 1,
                    borderColor: '#eee'
                },
                colors: [mainColor, secondaryColor]
            },
            plot;


    plot = $.plot($('.line-placeholder-3'), data, options);


    $("<div id='tooltip'></div>").css({
        position: "absolute",
        display: "none",
        border: "1px solid #95a4b8",
        padding: "4px",
        "font-size": "12px",
        color: "#fff",
        "border-radius": "4px",
        "background-color": "#95a4b8",
        opacity: 0.90
    }).appendTo("body");

    $(".chart-placeholder").bind("plothover", function (event, pos, item) {
        var str = "(" + pos.x.toFixed(2) + ", " + pos.y.toFixed(2) + ")";
        $("#hoverdata").text(str);

        if (item) {
            var x = item.datapoint[0],
                    y = item.datapoint[1];

            $("#tooltip").html(item.series.label + " : " + y)
                    .css({top: item.pageY + 5, left: item.pageX + 5})
                    .fadeIn(200);
        } else {
            $("#tooltip").hide();
        }
    });

}
