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
                    ARTICLE.initAuthorData(data.data.user);
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
    initAuthorData:function (author) {
        $("#author-name").html(author.username);
        $("#author-introduce").html(author.introduction);
    },
    initCommentData:function (data) {
        const comments = data.comment;
        const replies = data.reply;

        $.each(comments, function (index, comment) {
            $("#comment-list").append(
                "                <li>\n" +
                "                    <div class=\"common-box-row layui-row\">\n" +
                "                        <div class=\"common-headimg layui-col-md1 layui-col-xs2\">\n" +
                "                            <div class=\"commonimg-box\">\n" +
                "                                <img src=\"../static/layui/imgs/canreplace/headimg.jpg\">\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"common-info-row layui-col-md11 layui-col-xs10\">\n" +
                "                            <div class=\"common-nickname layui-row\">"+comment.commentTime+"<span>"+comment.username+"：</span></div>\n" +
                "                            <div class=\"common-text layui-row\">"+comment.content+"</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </li>"
            );
        });
    }

};

