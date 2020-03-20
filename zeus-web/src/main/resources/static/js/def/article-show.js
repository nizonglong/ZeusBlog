var surl = "http://localhost:8081";

function showMarkdown() {
    var content = $("#bc").val();
    document.getElementById("blog-content").innerHTML
        = marked(content);
}
