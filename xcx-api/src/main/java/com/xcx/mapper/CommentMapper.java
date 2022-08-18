package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.dto.CommentByPageDto;
import com.xcx.dao.dto.CommentLikesDto;
import com.xcx.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentByPageDto> selectAllByCommentsAndUsers(Long page);

    List<CommentByPageDto> selectCommentByCommentIdAndUsers(Integer userId , Integer commentId);

    void updateSawUp(Integer commentId);

    //帖子点赞+1
    void updateCommentLikesUp(Integer commentId);

    //查询点赞成功后后的赞数
    List<CommentLikesDto> selectLikesByCommentId(Integer commentId);

    //帖子点赞-1
    void updateCommentLikesDown(Integer commentId);

    //评论+1
    void updateCommentsUp(Integer commentId);
}
