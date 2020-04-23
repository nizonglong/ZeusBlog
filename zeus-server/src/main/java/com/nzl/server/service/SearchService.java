package com.nzl.server.service;

import com.nzl.common.pojo.ZeusResponseBean;

public interface SearchService {
    ZeusResponseBean titleSearch(String title);

    ZeusResponseBean contentSearch(String content);

    ZeusResponseBean userSearch(String username);
}
