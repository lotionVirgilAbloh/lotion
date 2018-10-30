//设置是通过网关还是本地测试
const isLocal = false;

(function () {
    //定义全局host的url
    if (!isLocal) {
        window.Config.webHostUrl = window.Config.webHostUrl + "control/";
    }
    //初始化textarea，若不初始化则会默认有一个空格
    document.getElementById("startsh").defaultValue = "";
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
                "dom": "<'row'<'col-sm-12 col-md-6'f>>" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
                "paging": false,
                "lengthChange": true,
                "processing": true,
                "serverSide": false,
                "searching": true,
                "info": false,
                "autoWidth": true,
                "ajax": window.Config.webHostUrl + "jobdao/of/getAll",
                "columns": [
                    {data: "jobname"}, {data: "type"}, {data: "destination"}
                ],
                "columnDefs": [
                    {
                        "targets": [3],
                        "render": function (data, type, full) {
                            if (full.lastrun != null) {
                                const date = full.lastrun.replace(/(\+\d{2})(\d{2})$/, "$1:$2");
                                return window.Config.dateFormat(new Date(date));
                            } else {
                                return "无日期记录";
                            }
                        }
                    },
                    {
                        "targets": [4],
                        "render": function (data, type, full) {
                            var jobname = "'" + full.jobname + "'";
                            var actionValue = "提交";
                            var actionClass = "btn m-btn m-btn--gradient-from-success m-btn--gradient-to-accent";
                            var buttonStyle = "margin-left: 5px";
                            var begin_div = '<div style="display: flex; flex-wrap: nowrap; justify-content: flex-start">';
                            var action = '<input type="button" value="' + actionValue + '" onclick="action(' + jobname + ')" class="' + actionClass + '" style="' + buttonStyle + '">';
                            var edit = '<input type="button" value="编辑" onclick="toupd(' + jobname + ')" class="btn m-btn m-btn--gradient-from-brand m-btn--gradient-to-info" style="' + buttonStyle + '">';
                            var del = '<input type="button" value="删除" onclick="del(' + jobname + ')" class="btn m-btn m-btn--gradient-from-focus m-btn--gradient-to-danger" style="' + buttonStyle + '">';
                            var end_div = '</div>';
                            return begin_div + action + edit + del + end_div;
                        }
                    }],
                "language": {
                    "processing": "<div style='text-align: center'><img src='" + window.Config.webStaticUrl + "/assets/lotion/media/img/lotion_logo_circuit_chroma_dynamic.gif' style='display: inline-block'></div>",
                    "search": "搜索:",
                    "emptyTable": "未找到任何条目",
                    "zeroRecords": "无匹配条目"
                }
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

function action(jobname) {
    $.ajax({
        url: window.Config.webHostUrl + "jobdao/of/action",
        data: {"jobname": jobname},
        async: true,
        type: "GET",
        dataType: 'json',
        success: function (result) {
            if (result != true) {
                alert('提交失败！');
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

function save() {
    var jobform = $("#jobform").serialize().replace(/%0D%0A/g, '%0A');
    console.log(jobform);
    $.ajax({
        url: window.Config.webHostUrl + "jobdao/of/save",
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
        url: window.Config.webHostUrl + "jobdao/of/getByJobname",
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
        url: window.Config.webHostUrl + "jobdao/of/deleteByJobname/" + jobname,
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