package com.xcx.controller;

import com.xcx.dao.vo.ArticleListVo;
import com.xcx.service.ArticleService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("selectAllArticle")
    public Result SelectAllArticle(@RequestBody ArticleListVo articleListVo){
        return articleService.SelectAllArticle(articleListVo);
    }

    @PostMapping("selectArticleByArticleId")
    public Result selectArticleByArticleId(@RequestParam Integer articleId){
        return articleService.selectArticleByArticleId(articleId);
    }



}
