//设置是通过网关还是本地测试
const isLocal = false;

(function () {
    //定义全局host的url
    if (!isLocal) {
        window.Config.webHostUrl = window.Config.webHostUrl + "log/";
    }
})();

//用于将rt_journal_div通过SSE连接到服务器端
(function (win, $) {

    var source = new EventSource(window.Config.webHostUrl + "exceptionsupervision/rtjournal");

    source.onopen = function (event) {
        $("#rt_journal_div").append("<p>" + "SSE连接建立成功..." + "</p>");
        $("#rt_journal_div").scrollTop( $("#rt_journal_div")[0].scrollHeight);
    };

    source.onerror = function (event) {
        $("#rt_journal_div").append("<p>" + "SSE连接发生错误，已断开..." + "</p>");
        $("#rt_journal_div").scrollTop( $("#rt_journal_div")[0].scrollHeight);
        source.close();
    };

    source.onmessage = function (event) {
        if (!(event.data === "[]")) {
            $("#rt_journal_div").append("<p>" + event.data + "</p>");
            $("#rt_journal_div").scrollTop( $("#rt_journal_div")[0].scrollHeight);
        }
    };
})(window, jQuery);
