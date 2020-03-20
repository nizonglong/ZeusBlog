// 用户操作
var USER = {
    param: {
        // 系统的url
        surl: "http://localhost:8081"
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
            url: USER.param.surl + "/user/checkUpdateData/" + $("#user-phone").val() + "/2",
            success: function (data) {
                if (data.status === 200) {
                    USER.doEditUser();
                } else if (data.status === 400) {
                    alert("手机号已经被占用，请更换号码");
                    $("#addPermission").select();
                } else {
                    alert("检测异常");
                }
            }
        });

    },
    doEditUser: function () {
        var formData = $("#userInfoForm").serialize();
        encodeURI(encodeURI(formData)); ///注意两次编码！！

        $.ajax({
            type: "post",
            dataType: "json",
            url: USER.param.surl + "/user/updateUserInfo",
            data: formData
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
    param: {
        // 系统的url
        surl: "http://localhost:8081"
    },
    showUpload: function () {
        var that = this;
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
                url: PIC_OP.param.surl + "/user/updateHeadPic",
                data: formData,
                cache: false,
                processData: false,
                contentType: false
            }).success(function (data) {
                if (data.status === 200) {
                    alert("头像修改成功！");
                    // 清空表单
                    window.location.href = "/main/index";
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