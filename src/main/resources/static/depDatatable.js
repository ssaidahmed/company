var urlAjax = "/api/department/";
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
                "render": function (data, type, row) {

                    if(data.parentDepartment === null) {
                        return "Нет род. отдела";
                    }

                    return data.parentDepartment.name;
                }
            },
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

    getForSelect(urlAjax, "parentDepartmentId");

    makeEditable();
} );
