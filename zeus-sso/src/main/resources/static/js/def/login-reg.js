const URLS = {
    server_url: "http://localhost:8082",
    web_url: "http://localhost:8080",
    sso_url: "http://localhost:8081"
};

const PAGE = {
    goReg: function () {
        window.location.href = "/signup";
    },
    goLogin: function () {
        window.location.href = "/login";
    },
    goIndex: function () {
        window.location.href = URLS.web_url;
    }
};

// 用户邮件验证
var EMAIL = {
    /**
     * 检测输入
     */
    checkInput: function () {
        if ($("#umail").val().length <= 0) {
            alert("请输入正确邮箱");
            return false
        }
        return true;
    },
    beforeSendEmail: function () {

        // 检查信息是否重复
        $.ajax({
            type: 'get',
            url: URLS.sso_url + "/user/check/" + $("#umail").val() + "/3",
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
        this.timeCount();

        const email = $("#umail").val();

        $.ajax({
            method: 'get',
            url: URLS.sso_url + "/user/sendActiveCode?email=" + email,
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
    timeCount: function () {
        $("#sendCode").attr("disabled", true);
        var starting = 60;
        var start1;
        var time = setInterval(function () {
            if (starting > 0) {
                starting--;
                start1 = starting > 10 ? starting : `0${starting}`
                $("#sendCode").html(`${start1}s后重新发送`);
            } else if (starting === 0) {
                $("#sendCode").html(`发送验证码`);
                clearInterval(time);
                $("#sendCode").attr("disabled", false);
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
const REG = {
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    doSubmit: function () {
        $.ajax({
            method: 'post',
            url: URLS.sso_url + "/user/register",
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
                alert(data.msg);
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
const LOGIN = {
    /**
     * 检测输入
     */
    checkInput: function () {
        return true;
    },
    doSubmit: function () {
        $.ajax({
            type: 'post',
            url: URLS.sso_url + "/user/doLogin",
            data: JSON.stringify(getFormData($("#loginForm"))),
            dataType: 'json',
            contentType: "application/json;charset=UTF-8"
        }).success(function (data) {
            if (data.status === 200) {
                PAGE.goIndex()
            } else {
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

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}