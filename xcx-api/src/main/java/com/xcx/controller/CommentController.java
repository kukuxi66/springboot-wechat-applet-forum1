package com.xcx.controller;

import com.xcx.dao.vo.CommentListVo;
import com.xcx.dao.vo.CommentSonVo;
import com.xcx.dao.vo.CommentVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.service.CommentAddressService;
import com.xcx.service.CommentService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentAddressService commentAddressService;

    //分页查询所有帖子
    @PostMapping("selectAllCommentByPage")
    public Result selectAllCommentByPage(@RequestBody CommentListVo commentListVo){
        return commentService.selectAllCommentByPage(commentListVo);
    }

    //查询帖子图片
    @GetMapping("selectCommentAddressByCommentId")
    public Result selectCommentAddressByCommentId(){
        return commentAddressService.selectCommentAddressByCommentId();
    }

    //根据帖子Id 查询帖子详情内容 查询成功后使文章浏览+1
    @PostMapping("selectCommentByCommentId")
    public Result selectCommentByCommentId(@RequestBody CommentVo commentVo){
        return commentService.selectCommentByCommentId(commentVo);
    }

    //发布帖子
    @PostMapping("commentWithOutAddressQianZhi")
    public Result commentWithOutAddressQianZhi(@RequestParam Integer userId,
                                               @RequestParam String commentTitle,
                                               @RequestParam String commentText,
                                               @RequestParam String commentCategoryName,
                                               @RequestParam String token
    ){

        return commentService.commentWithOutAddressQianZhi(userId,commentTitle,commentText,commentCategoryName,token);

    }

}
