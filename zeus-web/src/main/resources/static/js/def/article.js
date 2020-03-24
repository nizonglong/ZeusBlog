DETAIL = {
    json: ""
};

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

        $("#article-title").html(article.title);
        $("#article-content").html(article.content);
    },
    initAuthorData: function (author) {
        $("#author-name").html(author.username);
        $("#author-introduce").html(author.introduction);
    },
    initCommentData: function (data) {
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
                "                <li>" +
                "                    <div class=\"common-box-row layui-row\">" +
                "                        <div class=\"common-headimg layui-col-md1 layui-col-xs2\">" +
                "                            <div class=\"commonimg-box\">" +
                "                                <img src=\"../static/layui/imgs/canreplace/headimg.jpg\">" +
                "                            </div>" +
                "                        </div>" +
                "                        <div class=\"common-info-row layui-col-md11 layui-col-xs10\">" +
                "                            <div class=\"common-nickname layui-row\">" + comment.commentTime + "<span>" + comment.username + "：</span></div>" +
                "                            <div class=\"common-text layui-row\">" + comment.content + "</div>" +
                "                        </div>" +
                "                    </div>";

            // 评论内的回复
            let replyInfo = "                    <ul class=\"comment-box\">";
            // 回复的回复
            var replyToReplyInfo = "";

            $.each(comment.reply, function (index, reply) {
                if (reply.replyId == null) {
                    replyInfo +=
                        "                        <li>" +
                        "                            <div class=\"common-box-row layui-row\">" +
                        "                                <div class=\"common-headimg layui-col-md1 layui-col-xs2\">" +
                        "                                    <div class=\"commonimg-box\">" +
                        "                                        <img src=\"../static/layui/imgs/canreplace/headimg.jpg\">" +
                        "                                    </div>" +
                        "                                </div>" +
                        "                                <div class=\"common-info-row layui-col-md11 layui-col-xs10\">" +
                        "                                    <div class=\"common-nickname layui-row\">" + reply.time + "<span>" + reply.username + "：</span></div>" +
                        "                                    <div class=\"common-text layui-row\">" + reply.content + "</div>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "                            </div>" +
                        "                        </li>";
                }

                if (reply.replyId != null) {
                    replyToReplyInfo +=
                        "                        <li>" +
                        "                            <div class=\"common-box-row layui-row\">" +
                        "                                <div class=\"common-headimg layui-col-md1 layui-col-xs2\">" +
                        "                                    <div class=\"commonimg-box\">" +
                        "                                        <img src=\"../static/layui/imgs/canreplace/headimg.jpg\">" +
                        "                                    </div>" +
                        "                                </div>" +
                        "                                <div class=\"common-info-row layui-col-md11 layui-col-xs10\">" +
                        "                                    <div class=\"common-nickname layui-row\">" + reply.time + "<span>" + reply.username + "@" + reply.replyUsername + "：</span></div>" +
                        "                                    <div class=\"common-text layui-row\">" + reply.content + "</div>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "                            </div>" +
                        "                        </li>";
                }
            });
            replyInfo += replyToReplyInfo;
            replyInfo += "                    </ul>";


            // 添加末尾
            const endInfo = "                </li>";

            $("#comment-list").append(
                commentInfo + replyInfo + endInfo
            );
        });
    }

};

