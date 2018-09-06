//设置是通过网关还是本地测试
const isLocal = false;

(function () {
    //定义全局host的url
    if (!isLocal) {
        window.Config.webHostUrl = window.Config.webHostUrl + "log/";
    }
})();

var ansi_up = new AnsiUp;

//用于将rt_journal_div通过SSE连接到服务器端
(function (win, $) {

    var source = new EventSource(window.Config.webHostUrl + "exceptionsupervision/rtjournal");

    source.onopen = function (event) {
        $("#rt_journal_div").append("<p>" + "SSE连接建立成功..." + "</p>");
        $("#rt_journal_div").scrollTop($("#rt_journal_div")[0].scrollHeight);
    };

    source.onerror = function (event) {
        $("#rt_journal_div").append("<p>" + "SSE连接发生错误，已断开..." + "</p>");
        $("#rt_journal_div").scrollTop($("#rt_journal_div")[0].scrollHeight);
        source.close();
    };

    source.onmessage = function (event) {
        var data = event.data;
        if (!(data === "[]")) {
            var t_data = ansi_up.ansi_to_html(data);
            $("#rt_journal_div").append("<p>" + t_data + "</p>");
            $("#rt_journal_div").scrollTop($("#rt_journal_div")[0].scrollHeight);
        }
    };
})(window, jQuery);

(function (win, $) {
    var exceptionTable = {
        init: function () {
            this.initExceptionTable();
        },
        initExceptionTable: function () {
            var dttable = $('#exception_table').dataTable();
            dttable.fnClearTable(); //清空一下table
            dttable.fnDestroy(); //还原初始化了的datatable
            $("#exception_table").DataTable({
                "paging": true,
                "lengthChange": false,
                "pageLength": 10,
                "order": [ 1, 'desc' ],
                "processing": true,
                "serverSide": true,
                "searching": false,
                "info": false,
                "autoWidth": true,
                "ajax": {
                    "url": window.Config.webHostUrl + "exceptionsupervision/datatables",
                    "data": function (d) {
                        try {
                            if (d.order[0].column != undefined) {
                                if (d.order[0].column != 1) {
                                    d.order[0].column = 1;
                                    d.order[0].dir = "desc";
                                }
                            }
                        } catch (e) {
                            alert(e);
                        }
                    }
                },
                "columnDefs": [
                    {
                        "name": "exceptionID",
                        "targets": [0],
                        "orderable": false,
                        "render": function (data, type, full) {
                            return full.exceptionID;
                        }
                    },
                    {
                        "name": "timeMillis",
                        "targets": [1],
                        "orderable": true,
                        "render": function (data, type, full) {
                            return window.Config.timeFormat(new Date(full.timeMillis));
                        }
                    },
                    {
                        "name": "project",
                        "targets": [2],
                        "orderable": false,
                        "render": function (data, type, full) {
                            var source = "<p>project:" + full.project + "</p>";
                            var add = full.additionalProperties;
                            for (var key in add) {
                                source += "<p>" + key + ':' + add[key].toString() + "</p>";
                            }
                            return source;
                        }
                    },
                    {
                        "name": "message",
                        "targets": [3],
                        "orderable": false,
                        "render": function (data, type, full) {
                            return full.message;
                        }
                    },
                    {
                        "name": "action",
                        "targets": [4],
                        "orderable": false,
                        "render": function (data, type, full) {
                            var buttonStyle = "margin-left: 5px";
                            var begin_div = '<div style="display: flex; flex-wrap: nowrap; justify-content: flex-start">';
                            var track = '<input type="button" value="跟踪" onclick="track(' + full.exceptionID + ')" class="btn m-btn m-btn--gradient-from-brand m-btn--gradient-to-info" style="' + buttonStyle + '">';
                            var del = '<input type="button" value="删除" onclick="del(' + full.exceptionID + ')" class="btn m-btn m-btn--gradient-from-focus m-btn--gradient-to-danger" style="' + buttonStyle + '">';
                            var end_div = '</div>';
                            return begin_div + track + del + end_div;
                        }
                    }],
                "language": {
                    "processing": "<div style='text-align: center'><img src='" + window.Config.webStaticUrl + "/assets/lotion/media/img/lotion_logo_circuit_chroma_dynamic.gif' style='display: inline-block'></div>",
                    "emptyTable": "未找到任何条目",
                    "zeroRecords": "无匹配条目"
                }
            });
        }
    };
    win.ExceptionTable = exceptionTable;
})(window, jQuery);

(function () {
    window.ExceptionTable.init();
})();