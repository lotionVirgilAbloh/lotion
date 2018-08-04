var hostUrl;
//设置是通过网关还是本地测试
var isLocal = false;
(function () {
    //定义全局host的url
    if (isLocal) {
        hostUrl = window.location.protocol + "//" + window.location.host + '/';
    } else {
        hostUrl = window.location.protocol + "//" + window.location.host + '/' + "control/";
    }
    //初始化textarea
    document.getElementById("startsh").defaultValue = "";
    document.getElementById("stopsh").defaultValue = "";
})();
(function (win, $) {
    var job = {
        init: function () {
            this.initJobList();
        },
        initJobList: function () {
            var dttable = $('#job_table').dataTable();
            dttable.fnClearTable(); //清空一下table
            dttable.fnDestroy(); //还原初始化了的datatable
            $("#job_table").DataTable({
                "paging": false,
                "lengthChange": true,
                "processing": true,
                "serverSide": true,
                "searching": false,
                "info": false,
                "autoWidth": true,
                "ajax": hostUrl + "jobdao/rt/getAll",
                "columns": [
                    {data: "jobname"}, {data: "type"}, {data: "destination"}, {data: "status"}
                ],
                "columnDefs": [
                    {
                        "targets": [4],
                        "render": function (data, type, full) {
                            var jobname = "'" + full.jobname + "'";
                            var actionValue = "开启";
                            var actionClass = "btn m-btn m-btn--gradient-from-success m-btn--gradient-to-accent";
                            if (full.status === 1) {
                                actionValue = "停止";
                                actionClass = "btn m-btn m-btn--gradient-from-warning m-btn--gradient-to-danger";
                            }
                            var buttonStyle = "margin-left: 5px";
                            var begin_div = '<div style="display: flex; flex-wrap: nowrap; justify-content: flex-start">';
                            var action = '<input type="button" value="' + actionValue + '" onclick="toupd(' + jobname + ')" class="' + actionClass + '" style="'+ buttonStyle +'">';
                            var edit = '<input type="button" value="编辑" onclick="toupd(' + jobname + ')" class="btn m-btn m-btn--gradient-from-brand m-btn--gradient-to-info" style="'+ buttonStyle +'">';
                            var del = '<input type="button" value="删除" onclick="del(' + jobname + ')" class="btn m-btn m-btn--gradient-from-focus m-btn--gradient-to-danger" style="'+ buttonStyle +'">';
                            var end_div = '</div>';
                            return begin_div + action + edit + del + end_div;
                        }
                    }]
            })
        }
    };
    win.Job = job;
})(window, jQuery);

function showEditJobModal() {
    $('#jobname').attr("readonly", false);
    document.getElementById("jobform").reset();
    $('#editjob').modal('show');
}

function save() {
    var jobform = $("#jobform").serialize().replace(/%0D%0A/g, '%0A');
    console.log(jobform);
    $.ajax({
        url: hostUrl + "jobdao/rt/save",
        data: jobform,
        async: true,
        type: "POST",
        dataType: 'json',
        success: function (result) {
            if (result == true) {
                $('#editjob').modal('hide');
                document.getElementById("jobform").reset();
            }
            window.Job.init();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == 'error') {
                alert('网络通信失败，请稍后重试！');
            } else {
                alert(errorThrown + ',' + textStatus);
            }
        }
    });

}

function toupd(jobname) {
    $.ajax({
        url: hostUrl + "jobdao/rt/getByJobname",
        data: {"jobname": jobname},
        async: true,
        type: "GET",
        dataType: 'json',
        success: function (result) {
            $('#editjob').modal('show');
            $('#jobname').val(result.jobname);
            $('#jobname').attr("readonly", true);
            $('#username').val(result.username);
            $('#password').val(result.password);
            $('#startsh').val(result.startsh);
            $('#stopsh').val(result.stopsh);
            $('#destination').val(result.destination);
            $("#type").val(result.type);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == 'error') {
                alert('网络通信失败，请稍后重试！');
            } else {
                alert(errorThrown + ',' + textStatus);
            }
        }
    });
}

function del(jobname) {
    $.ajax({
        url: hostUrl + "jobdao/rt/deleteByJobname/" + jobname,
        async: true,
        type: "DELETE",
        dataType: 'json',
        success: function (result) {
            if (result == true) {
                window.Job.init();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == 'error') {
                alert('网络通信失败，请稍后重试！');
            } else {
                alert(errorThrown + ',' + textStatus);
            }
        }
    });

}