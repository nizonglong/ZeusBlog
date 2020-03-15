var ARTICLE_LIST = {
    param: {
        // 系统的url
        surl: "http://localhost:8082"
    },
    loadArticleList: function () {
        $.ajax({
            type: 'get',
            url: this.param.surl + "/article/getPageArticle",
            success: function (data) {
                if (data.status === 200) {
                    var json = data.data;
                    // console.log(json.list);
                    LOAD.initArticle(json.list);
                } else {
                    alert("获取文章失败！");
                }
            },
            error: function () {
                alert("Ajax 异常");
            }
        });
    },
    initArticle: function (articles) {
        $.each(articles, function (index, article) {
            $("#insert").append(
                "                                    <tr class=\"even gradeC\">\n" +
                "                                        <td>有适配微信小程序的计划吗</td>\n" +
                "                                        <td>天纵之人</td>\n" +
                "                                        <td>2016-09-26</td>\n" +
                "                                        <td>\n" +
                "                                            <div class=\"tpl-table-black-operation\">\n" +
                "                                                <a href=\"javascript:;\">\n" +
                "                                                    <i class=\"am-icon-pencil\"></i> 编辑\n" +
                "                                                </a>\n" +
                "                                                <a href=\"javascript:;\" class=\"tpl-table-black-operation-del\">\n" +
                "                                                    <i class=\"am-icon-trash\"></i> 删除\n" +
                "                                                </a>\n" +
                "                                            </div>\n" +
                "                                        </td>\n" +
                "                                    </tr>");
        });

    },
};

// MarkDown
var MD = {
    param: {
        // 系统的url
        surl: "http://localhost:8082"
    },
    submitMD: function (mdData) {
        var form = new FormData();
        var title = $("#at-title").val();
        var status = [];
        $('input[name="at-status"]:checked').each(function () {//遍历每一个名字为 status 的复选框，其中选中的执行函数
            status.push($(this).val());//将选中的值添加到数组status中
        });
        var digest = $("#digest").val();

        //jquery获取复选框值
        var keyword = [];//定义一个数组
        $('input[name="keyword"]:checked').each(function () {//遍历每一个名字为 keyword 的复选框，其中选中的执行函数
            keyword.push($(this).val());//将选中的值添加到数组keyword中
        });

        form.append('title', title);
        form.append('digest', digest);
        form.append('keyword', keyword.toLocaleString());
        form.append('mdData', mdData);
        form.append('status', status);

        $.ajax({
            method: 'post',
            url: this.param.surl + "/adminArticle/submitMd",
            data: form,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
            if (data.status === 200) {
                alert("文章发布成功！");
            } else {
                alert("文章发布异常！");
            }
        }).error(function () {
            alert("Ajax异常!");
        });
    }
};