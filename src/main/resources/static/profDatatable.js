var urlAjax = "/api/profession/";
var datatableApi;
$(document).ready(function() {

    datatableApi = $('#datatable').DataTable( {
        "processing": true,
        "serverSide": false,
        "paging": true,

        "ajax": {
            "url": urlAjax,
            "type": "GET",
            "dataSrc":""
        },
        "columns": [
            { data: "name" },
            { data: "description" },
            {
                "data": null,
                render:function(data, type, row)
                {
                    return '<button onclick="updateRow('+row.id+')" type="button" class="btn btn-dark btn-sm">изменить</button>';
                },
                "targets": -1
            },
            {
                "data": null,
                render:function(data, type, row)
                {
                    return '<button onclick="deleteRow('+ row.id +')" type="button" class="btn btn-danger btn-sm">удалить</button>';
                },
                "targets": -1
            }

        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    } );


    makeEditable();
} );
