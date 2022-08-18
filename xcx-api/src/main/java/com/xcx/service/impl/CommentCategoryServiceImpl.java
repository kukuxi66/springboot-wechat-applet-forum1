package com.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xcx.dao.dto.CommentByPageDto;
import com.xcx.dao.dto.CommentCategoryByPageDto;
import com.xcx.dao.pojo.CommentCategory;
import com.xcx.dao.vo.CommentCategoryListVo;
import com.xcx.mapper.CommentCategoryMapper;
import com.xcx.service.CommentCategoryService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentCategoryServiceImpl implements CommentCategoryService {

    @Autowired
    private CommentCategoryMapper commentCategoryMapper;

    //查询分类
    @Override
    public Result selectAllCommentCategoryNameAndCounts() {
        LambdaQueryWrapper<CommentCategory> commentCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<CommentCategory> commentCategories = commentCategoryMapper.selectList(commentCategoryLambdaQueryWrapper);
        return new Result(commentCategories);
    }

    @Override
    public Result selectCommentByCommentCategoryAndPage(CommentCategoryListVo commentCategoryListVo) {
        String commentCategoryName = commentCategoryListVo.getCommentCategoryName();
        //分页查询
        Long page = (commentCategoryListVo.getPage() - 1) * 10;
        List<CommentCategoryByPageDto> commentCategoryByPageDtoList = commentCategoryMapper.selectCategoryByCommentsAndUsers(page,commentCategoryName);
        //返回信息
        Map<String, List> listMap = new HashMap<>();
        listMap.put("commentDtoList",commentCategoryByPageDtoList);
        return new Result(listMap);
    }

}
