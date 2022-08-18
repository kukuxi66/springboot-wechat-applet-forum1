package com.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcx.dao.dto.ArticleDto;
import com.xcx.dao.dto.LikesDto;
import com.xcx.dao.pojo.Article;
import com.xcx.dao.pojo.Likes;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.ArticleListVo;
import com.xcx.dao.vo.LikesVo;
import com.xcx.mapper.ArticleMapper;
import com.xcx.mapper.LikesMapper;
import com.xcx.service.ArticleService;
import com.xcx.service.UserService;
import com.xcx.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.LanguageCallback;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    //查询所有文章
    @Override
    public Result SelectAllArticle(ArticleListVo articleListVo) {
        Page<Article> page = new Page<>(articleListVo.getPage(),articleListVo.getPagesize());
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.orderByDesc(Article::getArticleTime);
        Page<Article> articlePage = articleMapper.selectPage(page,articleLambdaQueryWrapper);
        List<Article> records =articlePage.getRecords();
        List<ArticleDto> articleDtos =copyList(records);
        return new Result(articleDtos);
    }

    //按文章ID查询文章
    @Override
    public Result selectArticleByArticleId(Integer articleId) {
        //查询文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getArticleId,articleId);
        List<Article> articleList = articleMapper.selectList(articleLambdaQueryWrapper);
        //文章浏览+1
        articleMapper.updateSawUp(articleId);
        //返回文章
        Map<String, List> articleIdDto = new LinkedHashMap<>();
        articleIdDto.put("articleIdDto",articleList);
        return new Result(articleIdDto);
    }


    private ArticleDto copy(Article article){
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article,articleDto);
        return articleDto;
    }

    private List<ArticleDto> copyList(List<Article> records){
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article record : records) {
            articleDtos.add(copy(record));
        }
        return articleDtos;
    }



}
