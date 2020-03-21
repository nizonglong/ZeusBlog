package com.nzl.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.impl.BaseServiceImpl;
import com.nzl.common.util.HttpClientUtil;
import com.nzl.common.util.JsonUtils;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.ArticleBlog;
import com.nzl.server.common.ServerConstant;
import com.nzl.server.service.ServerArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:57
 * @desc:
 * @version: 0.1
 **/
@Service
public class ServerArticleServiceImpl extends BaseServiceImpl<ArticleDto> implements ServerArticleService {

    @Resource
    private ArticleBlogMapper articleBlogMapper;

    @Override
    public ZeusResponseBean getPageArticles(int index, int pageSize) {
//        try {
//            //添加缓存逻辑
//            //从缓存中取商品信息，商品id对应的信息
//            String json = JsonUtils.get(ServerConstant.REDIS_ARTICLE_KEY + ":" + itemId + ":base");
//            //判断是否有值
//            if (!StringUtils.isBlank(json)) {
//                //把json转换成java对象
//                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
//                return TaotaoResult.ok(item);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        PageHelper.startPage(index, 10);
        List<ArticleDto> articles = articleBlogMapper.getPageArticles(index, pageSize);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>(articles);

        return ZeusResponseBean.ok(pageInfo);
    }


}
