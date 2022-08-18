package com.xcx.controller;

import com.xcx.dao.vo.CommentCategoryListVo;
import com.xcx.service.CommentCategoryService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentCategoryController {

    @Autowired
    private CommentCategoryService commentCategoryService;

    //查询分类
    @GetMapping("selectAllCommentCategoryNameAndCounts")
    public Result selectAllCommentCategoryNameAndCounts(){
        return commentCategoryService.selectAllCommentCategoryNameAndCounts();
    }

    //查询分类对应的文章
    @PostMapping("selectCommentByCommentCategoryAndPage")
    public Result selectCommentByCommentCategoryAndPage(@RequestBody CommentCategoryListVo commentCategoryListVo){

        return commentCategoryService.selectCommentByCommentCategoryAndPage(commentCategoryListVo);

    }

}
