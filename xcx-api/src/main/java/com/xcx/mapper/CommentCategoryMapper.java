package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.dto.CommentByPageDto;
import com.xcx.dao.dto.CommentCategoryByPageDto;
import com.xcx.dao.pojo.CommentCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentCategoryMapper extends BaseMapper<CommentCategory> {

    List<CommentCategoryByPageDto> selectCategoryByCommentsAndUsers(Long page , String commentCategoryName);

    Integer selectCategoryIdByCategoryName(String commentCategoryName);
}
