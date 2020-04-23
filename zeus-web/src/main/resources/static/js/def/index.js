const LOAD = {
    loadInitData: function (pageIndex) {
        $("#insert").html("");
        $("#pages").html("");
        // 加载index的Article初始信息, 默认初始加载200条信息
        $.ajax({
            type: 'get',
            url: URLS.server_url + "/article/timeArticles?index=" + pageIndex + "&pageSize=15",
            success: function (data) {
                if (data.status === 200) {
                    var json = data.data;
                    LOAD.initArticle(json.list);
                    LOAD.loadPageNum(json);
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert(data.msg);
            }
        });


        // 加载index的User初始信息
        $.ajax({
            type: 'get',
            url: URLS.web_url + "/user/getUser",
            success: function (data) {
                if (data.status === 200) {
                    LOAD.loadUser(data.data);
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert(data.msg);
            }
        });

        // 加载用户自定义标签
        // $.ajax({
        //     type: 'get',
        //     url: this.param.surl + "/main/getUserTag",
        //     success: function (data) {
        //         if (data.status === 200) {
        //             LOAD.loadUserTag(data.data);
        //         } else {
        //             alert("获取用户自定义标签失败！");
        //         }
        //     },
        //     error: function () {
        //         alert("Ajax 异常");
        //     }
        // });
    },
    initArticle: function (articles) {
        $.each(articles, function (index, article) {
            $("#insert").append("<article class=\"am-g blog-entry-article\">\n" +
                "            <div class=\"am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-img\">\n" +
                "                <img src=\"../static/i/f10.jpg\" alt=\"\" class=\"am-u-sm-12\">\n" +
                "            </div>\n" +
                "            <div class=\"am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-text\">\n" +
                "                <span><a href=\"javascript:void()\" class=\"blog-color\">article &nbsp;</a></span>\n" +
                "                <span>  &nbsp;@" + article.authorName + "</span>\n" +
                "                <span> " + article.blogTime + "</span>\n" +
                "                <h1><a href=\"javascript:void(0);\" onclick='ARTICLE.goDetail(" + article.articleBlogId + ")'>" + article.title + "</a></h1>\n" +
                "                <p>" + article.digest + "</p>\n" +
                "                <p><a href=\"\" class=\"blog-continue\">continue reading</a></p>\n" +
                "            </div>\n" +
                "        </article>\n");
        });

    },
    loadUser: function (user) {
        $("#index-head").attr("src", URLS.server_url + "/" + user.headPortraitUrl);
        $("#uname").html("<a href='/userInfo.html'>" + user.username + "</a>");
        $("#introduce").html(user.introduction);
    },
    // loadUserTag: function (userTags) {
    //     $.each(userTags, function (index, userTag) {
    //         $("#utag").append("<a href=\"\" class=\"blog-tag\">" + userTag.name + "</a>");
    //     });
    // },
    loadPageNum: function (json) {
        // 头
        if (json.hasPreviousPage) {
            $("#pages").append("<li class=\"am-pagination-prev\"><a href=\"#insert\" " +
                "onclick=\"LOAD.loadInitData(" + (json.pageNum - 1) + ")\">&laquo; Prev</a></li>");
        } else {
            $("#pages").append("<li class=\"am-pagination-prev\"><a href=\"javascript:void(0);\">&laquo; Prev</a></li>");
        }

        // 页码
        $.each(json.navigatepageNums, function (index, page) {
            if (json.pageNum === page) {
                $("#pages").append("<li class=\"am-pagination-prev am-active\"><a href=\"#insert\" " +
                    "onclick=\"LOAD.loadInitData(" + page + ")\">" + page + "</a></li>");
            } else {
                $("#pages").append("<li class=\"am-pagination-prev\"><a href=\"#insert\" " +
                    "onclick=\"LOAD.loadInitData(" + page + ")\">" + page + "</a></li>");
            }
        });

        // 尾
        if (json.hasNextPage) {
            $("#pages").append("<li class=\"am-pagination-prev\"><a href=\"#insert\" " +
                "onclick=\"LOAD.loadInitData(" + (json.pageNum + 1) + ")\">Next &raquo;</a></li>");
        } else {
            $("#pages").append("<li class=\"am-pagination-prev\"><a href=\"javascript:void(0);\">Next &raquo;</a></li>");
        }


    }
};