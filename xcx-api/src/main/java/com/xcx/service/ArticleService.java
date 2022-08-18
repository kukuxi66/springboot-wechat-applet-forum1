package com.xcx.service;

import com.xcx.dao.vo.ArticleListVo;
import com.xcx.utils.Result;

public interface ArticleService {

    Result SelectAllArticle(ArticleListVo articleListVo);

    Result selectArticleByArticleId(Integer articleId);

}
