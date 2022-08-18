package com.xcx.controller;

import com.xcx.dao.vo.CommentSonVo;
import com.xcx.dao.vo.CommentVo2;
import com.xcx.service.CommentSonService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentSonController {

    @Autowired
    private CommentSonService commentSonService;

    //根据帖子Id 查询帖子详情内容下的评论
    @PostMapping("selectCommentSonByCommentId")
    public Result selectCommentSonByCommentId(@RequestBody CommentSonVo commentSonVo){
        return commentSonService.selectCommentSonByCommentId(commentSonVo);
    }

    //评论
    @PostMapping("commentSon")
    public Result commentSon(@RequestBody CommentVo2 commentVo2){
        return commentSonService.commentSon(commentVo2);
    }

}
