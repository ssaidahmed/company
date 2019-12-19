var urlAjax = "/api/employee/";
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
            { data: "department.name"},
            { data: "profession.name"  },
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

    getForSelect("/api/department", "departmentId");
    getForSelect("/api/profession", "professionId");
    makeEditable();
} );
