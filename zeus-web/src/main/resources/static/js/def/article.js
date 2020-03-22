
const ARTICLE = {

    goDetail: function (id) {
        $.ajax({
            type: 'get',
            url: URLS.server_url + "/article/detail/" + id,
            success: function (data) {
                if (data.status === 200) {
                    console.log("1");
                    var json = data.data;
                    window.location = "/articlep";
                    ARTICLE.initDetail(json);
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert(data.msg);
            }
        });
    },
    initDetail:function (data) {
        $("#article-title222").html("data.title")


    },
    afterInitDetail:function () {
        document.title="11111";
        // $("#article-title222").html("data.title")
    }

};

$("#detail-test")
