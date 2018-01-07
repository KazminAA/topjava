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

var startDate = $("#startDate");
var endDate = $("#endDate");

startDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            maxDate: endDate.val() ? endDate.val() : false
        })
    }
});
endDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            minDate: startDate.val() ? startDate.val() : false
        })
    }
});
$("#startTime, #endTime").datetimepicker({
    datepicker: false,
    format: "H:i"
});
$("#dateTime").datetimepicker({
    format: "Y-m-d\\TH:i"
});