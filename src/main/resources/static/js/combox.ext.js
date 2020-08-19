function easychange (event) {
    console.log($(event).attr("ref"));
    var $ref = $("#" + $(event).attr("ref"));
    if ($ref.size() == 0) return false;
    $.ajax({
        type: 'POST',
        dataType: "json",
        url: $(event).attr("refUrl").replace("{value}", encodeURIComponent($(event).val())),
        cache: false,
        data: {},
        success: function (json) {
            _comboxRefresh($ref, json);
        },
        error: "请求失败"
    });
}

var _comboxRefresh = function ($select, json) {
    if (!json) return;
    var html = '';

    $.each(json, function (i) {
        if (json[i] && json[i].length > 1) {
            html += '<option value="' + json[i][0] + '">' + json[i][1] + '</option>';
        }
    });

    $select.html(html);

};



