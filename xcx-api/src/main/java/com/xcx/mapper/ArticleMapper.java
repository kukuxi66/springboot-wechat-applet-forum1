package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    //点赞成功后，使文章总赞数+1
    void updateLikesUp(Integer articleId);

    //点赞取消后，使文章总赞数-1
    void updateLikesDown(Integer articleId);

    //阅读文章，浏览+1
    void updateSawUp(Integer articleId);

}
