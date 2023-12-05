/**
 * 
 */


//页面初始化时获取数据
window.onload = function() {
	console.log('load');
	var email = sessionStorage.getItem("email");
	var passwd = sessionStorage.getItem("passwd");
	var cpasswd = sessionStorage.getItem("cpasswd");
	var name = sessionStorage.getItem("name");
	document.getElementById("reg-emailAddr").value = email;
	document.getElementById("reg-password").value = passwd;
	document.getElementById("reg-confirmPassword").value = cpasswd;
	document.getElementById("reg-userName").value = name;

};


function save() {
	console.log('save');
	var emailAddr = document.getElementById("reg-emailAddr").value;
	var passwd = document.getElementById("reg-password").value;
	var cpasswd = document.getElementById("reg-confirmPassword").value;
	var name = document.getElementById("reg-userName").value;
	sessionStorage.setItem("email", emailAddr);
	sessionStorage.setItem("passwd", passwd);
	sessionStorage.setItem("cpasswd", cpasswd);
	sessionStorage.setItem("name", name);
}

// 点链接时清空缓存
function clearSession() {
	console.log('clear');
	window.onunload = function() {
		console.log('onunload');
		sessionStorage.clear();
	}
}

// 发送验证码（通过调用servlet调用java方法实现）
function send() {
	var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
	var emailAddr = document.getElementById("reg-emailAddr").value;
	if (emailAddr == "" || !reg.test(emailAddr)) {
		window.alert("邮箱地址错误");
		return false;
	}
	console.log('send');
	window.location.href = "sendVerCode.do?role=Teacher&emailAddr=" + emailAddr;
};


// 检查密码
layui.use(function() {
	var $ = layui.$;
	var form = layui.form;
	var layer = layui.layer;
	var util = layui.util;

	form.verify({
		confirmPassword: function(value, item) {
			var passwordValue = $('#reg-password').val();
			if (value !== passwordValue) {
				return '两次密码输入不一致';
			}
		}
	});
});