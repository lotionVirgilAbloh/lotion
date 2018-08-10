(function(win, $) {
	var config = {
		webHostUrl : window.location.protocol + "//" + window.location.host + '/',
        getWebStaticUrl : function() {
            //设置默认静态文件服务器地址
            var url = "http://47.106.222.205:8080/tfile/lotion-ui/"
            $.ajax({
                url: this.webHostUrl + "getWebStaticUrl",
                async : false,
                data : "",
                type : "POST",
                dataType: 'text',
                success: function (result) {
                    url = result;
                }, error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == 'error') {
                        alert('JS获取静态资源地址失败！');
                    } else {
                        alert(errorThrown + ',' + textStatus);
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
            return Y + M + D;
        }
	};
	config.refreshWebStaticUrl();
	win.Config = config;
})(window, jQuery);
