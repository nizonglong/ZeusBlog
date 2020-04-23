const SEARCH = {
    doSearch: function () {
        $("#search-insert").html('')

        var name = $("#name-k").val();
        var value = $("#value-v").val();
        var url = URLS.server_url + "/search/getSearch?name=" + name + "&value=" + value;
        console.log(url)

        $.ajax({
            type: 'get',
            url: url,
            dataType: 'json',
            success: function (data) {
                if (data.status === 200) {
                    SEARCH.loadResult(data.data, name)
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert(data.msg);
            }
        });
    },
    loadResult: function (data, type) {

        if (type === 'title' || type === 'content') {
            SEARCH.loadRes(data)
        } else {
            SEARCH.loadUserRes(data)
        }
    },
    loadRes: function (articles) {
        $.each(articles, function (index, article) {
            $("#search-insert").append("<article class=\"am-g blog-entry-article\">\n" +
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
    loadUserRes: function (data) {
        $.each(data, function (index, user) {
            $("#search-insert").append("<article class=\"am-g blog-entry-article\">\n" +
                "            <div class=\"am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-img\">\n" +
                "                <img src=\"" + URLS.server_url + "/" + user.headPortraitUrl + "\" alt=\"\" height='300px' class=\"am-u-sm-12\">\n" +
                "            </div>\n" +
                "            <div class=\"am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-text\">\n" +
                "                <span><a href=\"javascript:void()\" class=\"blog-color\">user &nbsp;</a></span>\n" +
                "                <span>  &nbsp;@" + user.username + "</span>\n" +
                "                <span> " + user.joinTime + "</span>\n" +
                "                <h1><a href=\"javascript:void(0);\">" + user.username + "</a></h1>\n" +
                "                <p>" + user.introduction + "</p>\n" +
                "                <p><a href=\"\" class=\"blog-continue\">continue reading</a></p>\n" +
                "            </div>\n" +
                "        </article>\n");
        });
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