// 用户操作
const USER = {
    init: function () {
        $.ajax({
            method: 'get',
            url: URLS.web_url + "/user/getUser",
            success: function (data) {
                if (data.status === 200) {
                    USER.loadInit(data.data);
                } else {
                    alert("获取信息异常");
                }
            }
        });
    },
    loadInit: function (user) {
        $("#user-name").val(user.username);
        $("#uid").val(user.uid);
        $("#token").val(getCookie("ZEUS_TOKEN"));
        $("#real-name").val(user.realName);
        $("#user-email").val(user.email);
        $("#user-phone").val(user.phone);
        $("#user-birthday").val(user.birthday);
        $("#user-intro").val(user.introduction);
        var gender = '';
        if (user.gender === '0') {
            gender = '男'
        } else if (user.gender === '1') {
            gender = '女'
        } else {
            gender = '保密'
        }
        $("#user-gender").append("<option value=\"" + user.gender + "\" selected>" + gender + "</option>");
    },
    /**
     * 编辑用户信息
     */
    checkEditInput: function () {
        if ($("#real-name").val() === "") {
            alert("姓名不能为空");
            $("#real-name").focus();
            return false;
        }
        if ($("#user-phone").val() === "") {
            alert("手机号不能为空");
            $("#user-phone").focus();
            return false;
        } else if ($("#user-phone").val().length !== 11) {
            alert("手机号长度有误");
            $("#user-phone").focus();
            return false;
        }

        return true;
    },
    beforeEditUserSubmit: function () {
        // 检查信息是否重复
        $.ajax({
            url: URLS.web_url + "/user/checkUpdateData/" + $("#user-phone").val() + "/2",
            success: function (data) {
                if (data.status === 200) {
                    USER.doEditUser();
                } else if (data.status === 400) {
                    alert("手机号已经被占用，请更换号码");
                } else {
                    alert("检测异常");
                }
            }
        });

    },
    doEditUser: function () {

        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            url: URLS.web_url + "/user/update",
            data: JSON.stringify(getFormData($("#userInfoForm")))
        }).success(function (data) {
            if (data.status === 200) {
                alert("用户信息修改成功！");
                // 清空表单
                window.location.reload(); // 刷新当前页面.
            } else if (data.status === 400) {
                alert("用户信息修改失败！");
            } else {
                alert("用户信息修改异常！");
            }
        }).error(function () {
            alert("Ajax异常!");
        });

    },
    submitEditUser: function () {
        if (this.checkEditInput()) {
            this.beforeEditUserSubmit();
        }
    },

    /**
     * 更改头像
     */
    editHeader: function () {
        var formData = new FormData(document.getElementById("userHeaderForm"));

        var head = $("#user-pic").val();
        // 文件后缀名
        var extendName = head.substring(head.lastIndexOf(".") + 1, head.length);
        var isPic = false;

        if (extendName === "jpg") {
            isPic = true;
        } else if (extendName === "png") {
            isPic = true;
        } else if (extendName === "bmp") {
            isPic = true;
        } else if (extendName === "gif") {
            isPic = true;
        } else {
            alert("头像不可以是非图片文件！");
        }


        if (isPic) {
            $.ajax({
                method: 'post',
                url: USER.param.surl + "/user/updateUserInfo",
                data: formData,
                cache: false,
                processData: false,
                contentType: false
            }).success(function (data) {
                if (data.status === 200) {
                    alert("头像修改成功！");
                    // 清空表单
                    window.location.reload(); // 刷新当前页面.
                } else if (data.status === 400) {
                    alert("头像修改失败！");
                } else {
                    alert("头像修改异常！");
                }
            }).error(function () {
                alert("头像上传失败");
            });
        }


    }
};


var PIC_OP = {
    init: function () {
        $.ajax({
            method: 'get',
            url: URLS.web_url + "/user/getUser",
            success: function (data) {
                if (data.status === 200) {
                    PIC_OP.loadInit(data.data);
                } else {
                    alert("获取信息异常");
                }
            }
        });
    },
    loadInit: function (user) {
        $("#uid").val(user.uid);
        $("#picHead").attr("src", URLS.server_url + "/" + user.headPortraitUrl);
    },
    showUpload: function () {
        const main_file = document.getElementById('upFile');

        const fileObj = main_file.files[0];
        const windowURL = window.URL || window.webkitURL;
        const img = document.getElementById('picHead');
        console.log(windowURL);
        const dataURl = windowURL.createObjectURL(fileObj);
        console.log(dataURl);
        img.setAttribute('src', dataURl);
    },
    uploadPic: function () {
        const formData = new FormData(document.getElementById("headForm"));

        const head = $("#upFile").val();
        // 文件后缀名
        const extendName = head.substring(head.lastIndexOf(".") + 1, head.length);
        let isPic = false;

        if (extendName === "jpg") {
            isPic = true;
        } else if (extendName === "png") {
            isPic = true;
        } else if (extendName === "bmp") {
            isPic = true;
        } else if (extendName === "gif") {
            isPic = true;
        } else {
            alert("头像不可以是非图片文件！");
        }

        if (isPic) {
            $.ajax({
                method: 'post',
                url: URLS.server_url + "/user/updateHeadPic",
                data: formData,
                cache: false,
                processData: false,
                contentType: false
            }).success(function (data) {
                if (data.status === 200) {
                    alert("头像修改成功！");
                    // 清空表单
                    window.location.href = "/";
                } else if (data.status === 400) {
                    alert("头像修改失败！");
                } else {
                    alert("头像修改异常！");
                }
            }).error(function () {
                alert("头像上传失败");
            });
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

function getCookie(cname) {
    const name = cname + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}