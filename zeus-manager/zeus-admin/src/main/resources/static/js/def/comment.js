const COMMENT = {
    // 文章列表
    getComments: function () {
        // 重置数据
        $("#list-body").html("");
        $("#pages").html("");

        $.ajax({
            method: 'get',
            url: URLS.admin_url + "/comment/getComments?uid=" + getCookie("ZEUS_UID"),
            contentType: "application/json",
            success: function (data) {
                var json = data.data;
                COMMENT.initComment(json);
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    },
    initComment: function (comments) {
        $.each(comments, function (index, comment) {
            $("#comment-list").append(
                "                      <tr>\n" +
                "                        <td>\n" +
                "                          <label class=\"lyear-checkbox checkbox-primary\">\n" +
                "                            <input type=\"checkbox\" name=\"ids[]\" value=\"1\"><span></span>\n" +
                "                          </label>\n" +
                "                        </td>\n" +
                "                        <td>" + comment.blogName + "</td>\n" +
                "                        <td>" + comment.username + "</td>\n" +
                "                        <td>" + comment.content + "</td>\n" +
                "                        <td>" + comment.commentTime + "</td>\n" +
                "                        <td>\n" +
                "                          <div class=\"btn-group\">\n" +
                "                            <a class=\"btn btn-xs btn-default\" href=\"javascript:void()\" onclick='COMMENT.deleteOne(" + comment.articleCommentId + ",1)' title=\"删除\" data-toggle=\"tooltip\"><i class=\"mdi mdi-window-close\"></i></a>\n" +
                "                          </div>\n" +
                "                        </td>\n" +
                "                      </tr>"
            );

            $.each(comment.reply, function (index, reply) {
                $("#comment-list").append(
                    "                      <tr>\n" +
                    "                        <td>\n" +
                    "                          <label class=\"lyear-checkbox checkbox-primary\">\n" +
                    "                            <input type=\"checkbox\" name=\"ids[]\" value=\"1\"><span></span>\n" +
                    "                          </label>\n" +
                    "                        </td>\n" +
                    "                        <td>" + comment.blogName + "</td>\n" +
                    "                        <td>" + comment.username + "</td>\n" +
                    "                        <td>" + reply.content + "</td>\n" +
                    "                        <td>" + reply.time + "</td>\n" +
                    "                        <td>\n" +
                    "                          <div class=\"btn-group\">\n" +
                    "                            <a class=\"btn btn-xs btn-default\" href=\"javascript:void()\" onclick='COMMENT.deleteOne(" + reply.replyCommentId + ",2)' title=\"删除\" data-toggle=\"tooltip\"><i class=\"mdi mdi-window-close\"></i></a>\n" +
                    "                          </div>\n" +
                    "                        </td>\n" +
                    "                      </tr>"
                );
            });
        });
    },
    deleteOne: function (id, type) {
        const delBtn = confirm("确认删除此评论？");

        if (delBtn === true) {
            $.ajax({
                method: 'get',
                url: URLS.admin_url + "/comment/delete?id=" + id + "&type=" + type,
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