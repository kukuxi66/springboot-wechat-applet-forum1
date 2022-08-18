package com.xcx.service;

import com.xcx.dao.vo.LikesVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.utils.Result;

public interface LikesService {

    Result selectArticleLike(Integer articleId, Integer userId);

    Result articleLikes(LikesVo likesVo);

    //帖子点赞
    Result likes(LikesVo2 likesVo2);

    //查询该用户是否对该帖子点过赞
    Result selectCommentLikes(LikesVo2 likesVo2);
}
