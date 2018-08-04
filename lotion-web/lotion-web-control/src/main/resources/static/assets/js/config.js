(function() {
	var config = {
		Services : {
			'joblist' : '/jobdao/getall'
		},
		Domain : 'localhost:19084/',
		Ajax : function(url, data, type, success) {
			$.ajax({
				url : url,
				data : data,
				async : true,
				type : type,
				dataType : 'json',
				success : success,
				error : function(jqXHR, textStatus, errorThrown) {
					if (textStatus == 'error') {
						alert('网络通信失败，请稍后重试！');
					} else {
						alert(errorThrown + ',' + textStatus);
					}
				}
			});

		},
		//获取URL中的参数值
		GetQueryString : function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
			var context = "";
			if (r != null)
				context = r[2];
			reg = null;
			r = null;
			return context == null || context == "" || context == "undefined" ? "" : context;
		},
		getQuery : function(obj) {
			var group = [];
			$(":input", obj).not(":submit, :reset, :image,:button, [disabled]").each(function() {
				if (!this.name) return;

				if ($(this)[0].type == "checkbox" && $(this)[0].checked == false) return;
				if ($(this)[0].type == "radio" && $(this)[0].checked == false) return;
				if ($(this).val() == null || $(this).val() == "") return;
				if ($(this).attr("op") == undefined) return;
				if ($(this).attr("fieldtype") == undefined) return;

				group.push({
					op : $(this).attr("op"),
					field : this.name,
					value : ($(this)[0].type == "checkbox" || $(this)[0].type == "radio") ? ($(this)[0].checked == true ? 1 : 0) : $(this).val(),
					type : $(this).attr("fieldtype")
				});
			});
			var queryJson = "";
			if (group.length > 0) {
				queryJson = JSON.stringify(group);
			}

			return queryJson;
		},
		/**日期格式化*/
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
	window.Config = config;
})();
