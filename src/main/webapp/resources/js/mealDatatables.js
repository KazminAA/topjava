var ajaxUrl = "ajax/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData)
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
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
        format: "YYYY-MM-DDTHH:mm"
    });
});