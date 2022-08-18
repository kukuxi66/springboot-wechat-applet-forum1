package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.dto.CommentSonDto;
import com.xcx.dao.pojo.CommentSon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentSonMapper extends BaseMapper<CommentSon> {

    List<CommentSonDto> selectCommentSonByCommentId(Integer commentId);

}
