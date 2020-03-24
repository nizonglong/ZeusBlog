const ARTICLE = {

    goDetail: function (id) {
        window.location = URLS.web_url + "/article/" + id;
    },
    initDetail: function () {
        const url = window.location.pathname;
        const id = url.substring(9);
        ARTICLE.loadArticleData(id);
    },
    loadArticleData: function (id) {

        $.ajax({
            type: 'get',
            url: URLS.server_url + "/article/detail/" + id,
            success: function (data) {
                if (data.status === 200) {
                    ARTICLE.initArticleData(data.data.article);
                    ARTICLE.initAuthorData(data.data.author);
                    ARTICLE.initCommentData(data.data);
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert(data.msg);
            }
        });
    },
    initArticleData: function (article) {
        // 设置网页标题
        document.title = article.title + "-ZeusBlog";

        $("#article-id").val(article.articleBlogId);

        $("#article-title").html(article.title);
        $("#article-content").html(article.content);
    },
    initAuthorData: function (author) {
        $("#author-name").html(author.username);
        $("#author-introduce").html(author.introduction);
    },
    initCommentData: function (data) {
        $("#comment-to").hide();

        const comments = data.comment;
        // 填写评论数量
        let replyCount = 0;
        $.each(comments, function (index, comment) {
            replyCount += comment.reply.length;
        });
        const totalComment = comments.length + replyCount;
        $("#comment-count").html("共有" + totalComment + "条评论");

        $.each(comments, function (index, comment) {
            // 评论内容
            const commentInfo =
                "<li class= \"clearfix\">" +
                "  <div class=\"user\"><img alt=\"\" src=\"../static/style/images/art/a1.jpg\" class=\"avatar\" /></div>" +
                "  <div class=\"message\"> <a class=\"reply-link\" href=\"#comment-to\" onclick='COMMENT.replyComment("
                + comment.articleCommentId + ",\"" + comment.username + "\",\"" + comment.content + "\")'>Reply</a>" +
                "<div class=\"info\">" +
                "  <h2><a href=\"#\">" + comment.username + "</a></h2>" +
                "  <div class=\"meta\">" + comment.commentTime + "</div>" +
                "</div>" +
                "<p>" + comment.content + "</p>" +
                "  </div>";

            // 评论内的回复
            let replyInfo = "  <ul class=\"children\">";
            // 回复的回复
            var replyToReplyInfo = "<ul class=\"children\">";

            $.each(comment.reply, function (index, reply) {
                // 评论内的回复
                if (reply.replyId == null) {
                    replyInfo +=
                        "<li class= \"clearfix\">" +
                        "  <div class=\"user\"><img alt=\"\" src=\"../static/style/images/art/a2.jpg\" class=\"avatar\" /></div>" +
                        "  <div class=\"message\"> <a class=\"reply-link\" href=\"#comment-to\" onclick='COMMENT.replyReply("
                        + reply.articleCommentId + ",\"" + reply.username + "\",\"" + reply.content + "\",\"" + reply.replyCommentId + "\")'>Reply</a>" +
                        "<div class=\"info\">" +
                        "  <h2><a href=\"#\">" + reply.username + "</a></h2>" +
                        "  <div class=\"meta\">" + reply.time + "</div>" +
                        "</div>" +
                        "<p>" + reply.content + "</p>" +
                        "  </div>" +
                        "</li>";
                }

                // 回复的回复
                if (reply.replyId != null) {
                    replyToReplyInfo +=
                        "<li class= \"clearfix\">" +
                        "  <div class=\"user\"><img alt=\"\" src=\"../static/style/images/art/a3.jpg\" class=\"avatar\" /></div>" +
                        "  <div class=\"message\"> <a class=\"reply-link\" href=\"#comment-to\" onclick='COMMENT.replyReply("
                        + reply.articleCommentId + ",\"" + reply.username + "\",\"" + reply.content + "\",\"" + reply.replyCommentId + "\")'>Reply</a>" +
                        "<div class=\"info\">" +
                        "  <h2><a href=\"#\">" + reply.username + "@" + reply.replyUsername + "</a></h2>" +
                        "  <div class=\"meta\">" + reply.time + "</div>" +
                        "</div>" +
                        "<p>" + reply.content + "</p>" +
                        "  </div>" +
                        "</li>";
                }
            });
            replyToReplyInfo += "</ul>";
            replyInfo += replyToReplyInfo;
            replyInfo += "                    </ul>";


            // 添加末尾
            const endInfo = "</li>" +
                "  </ul>" +
                "</li>";

            $("#comment-list").append(
                commentInfo + replyInfo + endInfo
            );
        });
    }
};

const COMMENT = {
    replyReply: function (commentId, replyTo, replyContent, replyId) {
        // 显示回复评论信息
        if (replyContent.length > 10) {
            console.log("replyContent-cut");
            replyContent = replyContent.substring(0, 10) + "...";
        }

        $("#comment-to").show()
            .val("回复@" + replyTo + ":" + replyContent);

        // 更改评论类型为恢复评论
        $("#comment-type").val("replyReply");
        $("#comment-id").val(commentId);
        $("#reply-id").val(replyId);
    },
    replyComment: function (commentId, replyTo, commentContent) {
        // 显示回复评论信息
        if (commentContent.length > 10) {
            commentContent = commentContent.substring(0, 10) + "...";
        }
        $("#comment-to").show()
            .val("回复@" + replyTo + ":" + commentContent);

        // 更改评论类型为恢复评论
        $("#comment-type").val("reply");
        $("#comment-id").val(commentId);
    },

    // 下面是提交评论一系列方法
    checkComment: function () {
        if ($("#comment-text").val().trim().length === 0) {
            alert('评论内容不能为空');
            return false;
        }
        return true;
    },
    judgeComment: function () {
        const form = new FormData();
        let url = "";
        if ($("#comment-type").val() === "comment") {
            url = URLS.web_url + "/article/comment";
            form.append('articleBlogId', $("#article-id").val());
        } else if ($("#comment-type").val() === "reply") {
            url = URLS.web_url + "/article/reply";
            form.append('articleCommentId', $("#comment-id").val());
        } else if ($("#comment-type").val() === "replyReply") {
            url = URLS.web_url + "/article/reply";
            form.append('articleCommentId', $("#comment-id").val());
            form.append('replyId', $("#reply-id").val());
        }

        // 公共属性
        form.append('content', $("#comment-text").val());
        form.append('uid', getCookie("ZEUS_TOKEN"));

        // 最终提交数据
        COMMENT.submitComment(url, form);
    },
    submitComment: function (url, commentFormData) {

        $.ajax({
            method: 'post',
            url: url,
            data: convert_FormData_to_json(commentFormData),
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                alert(data.data);
                window.location.reload();
            },
            error: function (data) {
                alert(data.msg)
            }
        });
    },
    doSubmit: function () {
        if (COMMENT.checkComment()) {
            COMMENT.judgeComment();
        }
    },
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