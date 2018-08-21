(function(win, $) {
	var config = {
		webHostUrl : window.location.protocol + "//" + window.location.host + '/',
        getWebStaticUrl : function() {
            //设置默认静态文件服务器地址
            var url = "http://47.106.222.205:8080/tfile/lotion-ui/"
            $.ajax({
                url: this.webHostUrl + "getWebStaticUrl",
                async : true,
                data : "",
                type : "POST",
                dataType: 'text',
                success: function (result) {
                    url = result;
                }, error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == 'error') {
                    } else {
                    }
                }
            });
            return url;
        },
        refreshWebStaticUrl : function() {
            this.webStaticUrl = this.getWebStaticUrl();
        },
		webStaticUrl : "",
        dateFormat : function(date) {
            Y = date.getFullYear() + '-';
            M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
                    .getMonth() + 1)
                + '-';
            D = date.getDate().toString().length == 1 ? '0' + date.getDate()
                : date.getDate();
            h = (date.getHours().toString().length == 1 ? '0' + date.getHours()
                : date.getHours())
                + ':';
            m = (date.getMinutes().toString().length == 1 ? '0' + date.getMinutes()
                : date.getMinutes())
                + ':'
            s = date.getSeconds().toString().length== 1 ? '0' + date.getSeconds()
                : date.getSeconds();
            return Y + M + D + ' ' + h + m + s;
        }
	};
	config.refreshWebStaticUrl();
	win.Config = config;
})(window, jQuery);
