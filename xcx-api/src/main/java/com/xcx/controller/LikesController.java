package com.xcx.controller;

import com.xcx.dao.vo.LikesVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.service.ArticleService;
import com.xcx.service.LikesService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikesController {

    @Autowired
    private LikesService likesService;

    @PostMapping("selectArticleLike")
    public Result selectArticleLike(@RequestParam Integer articleId , @RequestParam Integer userId ){
        return likesService.selectArticleLike(articleId,userId);
    }

    @PostMapping("articleLikes")
    public Result articleLikes(@RequestBody LikesVo likesVo){
        return likesService.articleLikes(likesVo);
    }

    //查询该用户是否对该帖子点过赞
    @PostMapping("selectCommentLikes")
    public Result selectCommentLikes(@RequestBody LikesVo2 likesVo2 ){
        return likesService.selectCommentLikes(likesVo2);
    }

    //帖子点赞
    @PostMapping("likes")
    public Result likes(@RequestBody LikesVo2 likesVo2){
        return likesService.likes(likesVo2);
    }

}
