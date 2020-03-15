var PAGE = {
    goReg: function () {
        window.location.href = "/page/showReg";
    },
    goLogin: function () {
        window.location.href = "/page/showLogin";
    }
};

// 用户邮件验证
var EMAIL = {
    param: {
        // 系统的url
        surl: "http://localhost:8080"
    },
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    beforeSendEmail: function () {

        // 检查信息是否重复
        $.ajax({
            type: 'get',
            url: this.param.surl + "/check/" + $("#umail").val() + "/3",
            success: function (data) {
                if (data.status === 200 && data.msg === "OK") {
                    EMAIL.doSendEmail();
                } else {
                    alert("邮箱已存在，请检查是否已注册。");
                }
            },
            error: function () {
                alert("Ajax 异常");
            }
        });


    },
    doSendEmail: function () {
        var email = $("#umail").val();
        console.log(email);

        $.ajax({
            method: 'get',
            url: this.param.surl + "/sendActiveEmail?email=" + email,
            data: email,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
            if (data.status === 200) {
                alert("邮件发送成功！");
            } else {
                alert("邮件发送异常！");
            }
        }).error(function () {
            alert("Ajax异常!");
        });

    },
    sendEmail: function () {
        if (this.checkInput()) {
            this.beforeSendEmail();
        }
    }
};

// 用户注册
var REG = {
    param: {
        // 系统的url
        surl: "http://localhost:8081"
    },
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    doSubmit: function () {
        var form = $("#regForm");
        encodeURI(encodeURI(form)); ///注意两次编码！！
        var formData = new FormData(document.getElementById("regForm"));

        console.log("注册表单结果=" + formData);

        $.ajax({
            method: 'post',
            url: this.param.surl + "/register",
            data: formData,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
            if (data.status === 200) {
                alert("恭喜，注册成功！快去登录吧！");
                window.location.href = "/page/showLogin";
            } else if (data.status === 500) {
                alert("注册异常！");
            } else {
                alert(data.data);
            }
        }).error(function () {
            alert("Ajax异常!");
        });

    },
    submit: function () {
        if (this.checkInput()) {
            this.doSubmit();
        }
    }
};

// 用户登录
var LOGIN = {
    param: {
        // 系统的url
        surl: "http://localhost:8080"
    },
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    doSubmit: function () {
        var formData = new FormData(document.getElementById("loginForm"));

        $.ajax({
            method: 'post',
            url: this.param.surl + "/login",
            data: formData,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
            if (data.status === 200 || data.length > 20) {
                window.location.href = "/main/index";
            } else if (data.status === 400) {
                alert(data.msg);
            }
        }).error(function () {
            alert("Ajax异常!");
        });

    },
    submit: function () {
        if (LOGIN.checkInput()) {
            LOGIN.doSubmit();
        }
    }
};