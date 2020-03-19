var PAGE = {
    goReg: function () {
        window.location.href = "/login";
    },
    goLogin: function () {
        window.location.href = "/signup";
    }
};

// 用户邮件验证
var EMAIL = {
    param: {
        // 系统的url
        surl: "http://localhost:8081"
    },
    /**
     * 检测输入
     */
    checkInput: function () {
        if ($("#umail").val().length <= 0) {
            alert("请输入正确邮箱")
            return false
        }
        return true;
    },
    beforeSendEmail: function () {

        // 检查信息是否重复
        $.ajax({
            type: 'get',
            url: this.param.surl + "/check/" + $("#umail").val() + "/3",
            success: function (data) {
                if (data.status === 200) {
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
        this.timeCount()

        const email = $("#umail").val();

        $.ajax({
            method: 'get',
            url: this.param.surl + "/sendActiveCode?email=" + email,
            data: email,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
            alert(data.data);
        }).error(function () {
            alert("Ajax异常!");
        });
    },
    timeCount:function() {
        $("#sendCode").attr("disabled",true);
        var starting = 60;
        var start1;
        var time = setInterval(function () {
            if (starting > 0) {
                starting--;
                start1 = starting > 10 ? starting:`0${starting}`
                $("#sendCode").html(`${start1}s后重新发送`);
            } else if (starting === 0) {
                $("#sendCode").html(`发送验证码`);
                clearInterval(time);
                $("#sendCode").attr("disabled",false);
            }
        }, 1000)
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
        $.ajax({
            method: 'post',
            url: this.param.surl + "/register",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(getFormData($("#regForm"))),
            dataType: 'json',
            cache: false,
            processData: false
        }).success(function (data) {
            if (data.status === 200) {
                alert("恭喜，注册成功！快去登录吧！");
                PAGE.goLogin()
            } else {
                alert(data.message);
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
        surl: "http://localhost:8081"
    },
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    doSubmit: function () {
        $.ajax({
            type: 'post',
            url: this.param.surl + "/doLogin",
            data: JSON.stringify(getFormData($("#loginForm"))),
            dataType:'json',
            contentType: "application/json;charset=UTF-8"
        }).success(function (data) {
            if (data.status === 200) {
                alert(data.data);
            } else {
                alert(data.data);
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

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}