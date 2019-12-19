var form;
function makeEditable() {
    form = $("#form_add_entity");
    $('#datatable tbody').on('click', 'button[name="delete"]', function () {
        deleteRow($(this).attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {

        console.log(jqXHR);
    });


    $.ajaxSetup({cache: false});
}



function deleteRow(id) {
    $.ajax({
        url: urlAjax + id,
        type: "DELETE",
        success: function () {
            updateTable();
            alert("удален");
        }
    });
}

function add() {

    form.find(":input").val("");
    $(form).trigger("reset");
    var forDepSelectTag = $("#parentDepartmentId");

    if(forDepSelectTag.length!==0){

        forDepSelectTag.prop("disabled", false);
    }
    $("#editRow").modal();
}

function updateTable() {
    $.get(urlAjax, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}


function updateRow(id) {

    $.get(urlAjax  + id, function (data) {
        $.each(data, function (key, value) {

            if(key === 'department'){
                $("#departmentId").val(value.id);
                return;

            }
            if(key === 'profession'){
                $("#professionId").val(value.id);
                return;
            }
            if(key === 'parentDepartment'){
                var selectTag = $("#parentDepartmentId");
                if(value !== null){

                    selectTag.val(value.id);
                }else {
                    selectTag.prop("disabled", true);
                }
                return;
            }

            form.find("input[name='" + key + "']").val(value);
        });
        var row = $("#editRow");
        if((row.length !== 0)){

            row.modal();
        }
    });


}
function getFormData(formId){
    var unindexed_array = $(formId).serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}
function save() {
    var formData = getFormData(form);
    for(var key in formData){
        if(key === 'parentDepartmentId'){
            continue;
        }
        if(key !== 'id' && formData[key] === ''){
            alert("поля должны быть заполненны");
            return;
        }
    }
    $.ajax({

        type: formData.id === ''? "POST": "PUT",
        contentType: "application/json",
        url: urlAjax,
        data: JSON.stringify(formData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function () {

            console.log(JSON.stringify(formData));
            updateTable();
            $(form).trigger("reset");
            $("#editRow").modal('hide');
            alert("Объект сохранен");

        },
        error: function (e) {
            console.log(JSON.stringify(formData));
            $("#editRow").modal('hide');
            alert("Произошла ошибка");

        }
    });
}

function getForSelect(url, id_element) {
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json',

        error: function () {
            alert("Не удалось подгрузить данные");
        },
        success: function (data) {

            var $mySelect = $('#'+id_element);
            $.each(data, function(key, value) {
                var $option = $("<option/>", {
                    value: value.id,
                    text: value.name
                });
                $mySelect.append($option);
            });
        }
    });
}


