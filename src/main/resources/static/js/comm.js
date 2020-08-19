var myAjax = {

    ASuper: function (url, data, sucfun, errfun, comfun,async) {
            $.ajax({
                type: "POST",
                url: basePath + url,
                data: data,
                async: (async == null || async == "") ? true : async,
                dataType: "json",
                cache: false,
                success: sucfun,
                error: errfun,
                complete: comfun
            });
        }
}

function myAjaxs() {
    var len = arguments.length;
    if (len <= 0) {
        return;
    }
    if (len == 1) {
        myAjax.ASuper(arguments[0], null, null, null, null,null);
    } else if (len == 2) {
        if (typeof arguments[1] == 'function') {
            myAjax.ASuper(arguments[0], null, arguments[1], null, null,null);
        } else {
            myAjax.ASuper(arguments[0], arguments[1], null, null, null,null);
        }
    } else if (len == 3) {
        if (typeof arguments[2] == 'function') {
            myAjax.ASuper(arguments[0], arguments[1], arguments[2], null, null,null);
        } else {
            myAjax.ASuper(arguments[0], arguments[1], null, null, null,arguments[2]);
        }
    } else if (len == 4) {
        myAjax.ASuper(arguments[0], arguments[1], arguments[2], arguments[3], null,null);
    } else if (len == 5) {
        myAjax.ASuper(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4],null);
    }
}

/*function myAjax() {
    myAjax_Super(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
}*/


var load = {
    loadshow: function (ActionMode, url, data, callfun) {
        $("#" + ActionMode).load(url, data, function () {
            // setTimeout(function () {
            //     $("#" + ActionMode).children().modal('show');
            // }, 120);
            // $("#"+ActionMode).children().on('show.bs.modal', function () {
            //
            // });
            //
            // $("#"+ActionMode).children().on('hidden.bs.modal', function () {
            //     $("#"+ActionMode).children().remove();
            // });
        });
    },

    load: function (ActionMode, url, data) {
        $("#" + ActionMode).load( url, data);
    }
}




//时间显示
function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
// add a zero in front of numbers<10
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
    setTimeout(startTime, 500);
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i
    }
    ;
    return i;
}


//时间生成器
function time() {
    var today = new Date();
    var y = today.getFullYear();
    var mo = (today.getMonth() + 1);
    var d = today.getDate();
    var h = today.getHours();
    var mi = today.getMinutes();
    var s = today.getSeconds();
    var time = y + "/" + mo + "/" + d + " " + h + ":" + mi + ":" + s;
    return time;
}

//自定义弹窗
function AlertMessage(alertMsg) {
    $('#AlertMessage h3').text(alertMsg);
    $('#AlertMessage').modal('show');
    setTimeout(function () {
        $('#AlertMessage').modal('hide');
    }, 1200);
}

//去两边空格
$("input").blur(function () {
    this.value = this.value.replace(/^\s\s*/, '').replace(/\s\s*$/, '').replace(/-*/g, '')
});

//去空格
function kongge(konggee) {
    return konggee.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}