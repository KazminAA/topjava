var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return data.substring(0, 16).replace("T", " ");
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? "exceeded" : "normal");
        },
        "initComplete": makeEditable
    });
});

$(document).ready(function () {
    var startDate = $("#startDate");
    var endDate = $("#endDate");

    startDate.datetimepicker({
        format: 'YYYY-MM-DD',
        maxDate: endDate.val() ? endDate.val() : false
    });
    endDate.datetimepicker({
        format: 'YYYY-MM-DD',
        minDate: startDate.val() ? startDate.val() : false
    });
    $("#startTime, #endTime").datetimepicker({
        format: "HH:mm"
    });
    $("#dateTime").datetimepicker({
        format: "YYYY-MM-DDTHH:mm",
        sideBySide: true
    });
});