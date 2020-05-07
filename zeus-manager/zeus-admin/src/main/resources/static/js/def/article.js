const TYPE = {
    setTypeSelector: function (types) {
        $.each(types, function (index, type) {
            $("#type").append(
                "<option value=\"" + type.blogTypeId + "\">" + type.blogTypeName + "</option>"
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
    checkData: function () {
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
    doSubmitArticle: function (type) {
        const form = new FormData(document.getElementById("article-form"));
        const content = tinyMCE.activeEditor.getContent();

        form.append('uid', getCookie("ZEUS_UID"));
        form.append('content', content);

        let url_submit = URLS.admin_url + "/article/new";
        if (type === 'edit') {
            url_submit = URLS.admin_url + "/article/update";
        }

        $.ajax({
            method: 'post',
            url: url_submit,
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
    submitArticle: function (type) {
        if (ARTICLE.checkData()) {
            ARTICLE.doSubmitArticle(type);
        }
    },
    // 文章列表
    getList: function (pageIndex) {
        // 重置数据
        $("#list-body").html("");
        $("#pages").html("");

        $.ajax({
            method: 'get',
            url: URLS.server_url + "/article/list?uid=" + getCookie("ZEUS_UID") + "&index=" + pageIndex + "&pageSize=15",
            contentType: "application/json",
            success: function (data) {
                var json = data.data;
                ARTICLE.initList(json.list);
                ARTICLE.loadPageNum(json);
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    },
    initList: function (articles) {
        $.each(articles, function (index, article) {
            $("#list-body").append("<tr>\n" +
                "                        <td>\n" +
                "                          <label class=\"lyear-checkbox checkbox-primary\">\n" +
                "                            <input type=\"checkbox\" name=\"ids[]\" value=\"1\"><span></span>\n" +
                "                          </label>\n" +
                "                        </td>\n" +
                "                        <td>" + article.articleBlogId + "</td>\n" +
                "                        <td>" + (article.title.length>10?article.title.substr(0,10)+"......":article.title) + "</td>\n" +
                "                        <td>" + (article.digest.length>10?article.digest.substr(0,10)+"......":article.digest) + "</td>\n" +
                "                        <td>" + article.typeName + "</td>\n" +
                "                        <td>" + article.blogTime + "</td>\n" +
                "                        <td><font class=\"text-success\">" + article.status + "</font></td>\n" +
                "                        <td>\n" +
                "                          <div class=\"btn-group\">\n" +
                "                            <a class=\"btn btn-xs btn-default\" href=\"javascript:void()\" onclick='ARTICLE.goEdit(" + article.articleBlogId + ")' title=\"编辑\" data-toggle=\"tooltip\"><i class=\"mdi mdi-pencil\"></i></a>\n" +
                "                            <a class=\"btn btn-xs btn-default\" href=\"javascript:void()\" onclick='ARTICLE.deleteOne(" + article.articleBlogId + ")' title=\"删除\" data-toggle=\"tooltip\"><i class=\"mdi mdi-window-close\"></i></a>\n" +
                "                          </div>\n" +
                "                        </td>\n" +
                "                      </tr>");
        });
    },
    loadPageNum: function (json) {
        // 头
        if (json.hasPreviousPage) {
            $("#pages").append("<li><span onclick='getList(" + (json.pageNum - 1) + ")'>«</span></li>");
        } else {
            $("#pages").append("<li class=\"disabled\"><span>«</span></li>");
        }

        // 页码
        $.each(json.navigatepageNums, function (index, page) {
            if (json.pageNum === page) {
                $("#pages").append("<li class=\"active\"><a href=\"javascript:void(0);\" " +
                    "onclick=\"ARTICLE.getList(" + page + ")\">" + page + "</a></li>");
            } else {
                $("#pages").append("<li><a href=\"javascript:void(0);\" " +
                    "onclick=\"ARTICLE.getList(" + page + ")\">" + page + "</a></li>");
            }
        });

        // 尾
        if (json.hasNextPage) {
            $("#pages").append("<li><a href=\"#list-body\" " +
                "onclick=\"ARTICLE.getList(" + (json.pageNum + 1) + ")\">» </a></li>");
        } else {
            $("#pages").append("<li><a href=\"javascript:void(0);\">» </a></li>");
        }
    },
    deleteOne: function (id) {
        const delBtn = confirm("确认删除此文章？");

        if (delBtn === true) {
            $.ajax({
                method: 'get',
                url: URLS.admin_url + "/article/delete/" + id,
                contentType: "application/json",
                success: function (data) {
                    alert(data.data);
                    window.location.reload();
                },
                error: function (data) {
                    alert(data.msg)
                }
            });
        } else {
            alert("删除操作取消！");
        }
    },
    goEdit: function (id) {
        window.location = URLS.admin_url + "/edit/" + id;
    },
    getEdit: function () {
        const url = window.location.pathname;
        const id = url.substring(6);

        $.ajax({
            method: 'get',
            url: URLS.server_url + "/article/detail/" + id,
            contentType: "application/json",
            success: function (data) {
                ARTICLE.initArticleDetail(data.data.article)
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    },
    initArticleDetail: function (article) {
        $("#article-id").val(article.articleBlogId);
        $("#title").val(article.title);
        $("#digest").val(article.digest);
        $("#type").val(article.blogTypeId);
        tinyMCE.activeEditor.setContent(article.content);
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