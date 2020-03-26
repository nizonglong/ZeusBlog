const TYPE = {
    setTypeSelector: function (types) {
        $.each(types, function (index, type) {
            $("#type").append(
                "<option value=\"1\">" + type.blogTypeName + "</option>"
            );
        });
    },
    getType: function () {
        $.ajax({
            method: 'get',
            url: URLS.server_url + "/type/getAllType",
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                TYPE.setTypeSelector(data.data);
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    }
};


const ARTICLE = {
    checkData: function (html) {
        if ($("#title").val().length === 0) {
            alert("标题不能为空！");
            return false;
        }

        if ($("#digest").val().length === 0) {
            alert("概要不能为空！");
            return false;
        }

        const content = tinyMCE.activeEditor.getContent();
        if (content.length === 0) {
            alert("内容不能为空！");
            return false;
        }
        return true;
    },
    doSubmitArticle:function () {
        const form = new FormData(document.getElementById("article-form"));
        const content = tinyMCE.activeEditor.getContent();

        form.append('uid', getCookie("ZEUS_UID"));
        form.append('content', content);

        console.log(convert_FormData_to_json(form));

        $.ajax({
            method: 'post',
            url: URLS.server_url + "/article/new",
            data: convert_FormData_to_json(form),
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                alert(data.data);
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    },
    submitArticle:function (html) {
        if (ARTICLE.checkData(html)) {
            ARTICLE.doSubmitArticle();
        }
    }
};

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

function convert_FormData_to_json(formData) {
    const objData = {};

    for (const entry of formData.entries()) {
        objData[entry[0]] = entry[1];
    }
    return JSON.stringify(objData);
}