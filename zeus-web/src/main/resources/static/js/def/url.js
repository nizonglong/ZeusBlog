const URLS = {
    server_url: "http://localhost:8082",
    web_url: "http://localhost:8080",
    sso_url: "http://localhost:8081",
    admin_url: "http://localhost:8083"
};

const PAGE = {
    goAdmin: function () {
        window.location.href = URLS.admin_url;
    }
};