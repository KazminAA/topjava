var ajaxUrl = "ajax/admin/users/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData)
}

function enable(id) {
    var checked = $("#" + id).is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "enabled=" + checked,
        success: function () {
            checked ? successNoty("Enabled") : successNoty("Disabled");
        }
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});