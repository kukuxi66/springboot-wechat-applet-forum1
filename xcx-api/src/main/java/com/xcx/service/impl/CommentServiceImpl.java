package com.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xcx.dao.dto.CommentByPageDto;
import com.xcx.dao.pojo.Comment;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.CommentListVo;
import com.xcx.dao.vo.CommentVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.mapper.CommentCategoryMapper;
import com.xcx.mapper.CommentMapper;
import com.xcx.service.CommentService;
import com.xcx.service.UserService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentCategoryMapper commentCategoryMapper;

    //查询帖子列表
    @Override
    public Result selectAllCommentByPage(CommentListVo commentListVo) {
        //分页查询
        Long page = (commentListVo.getPage() - 1) * 10;
        List<CommentByPageDto> commentByPageDtoList = commentMapper.selectAllByCommentsAndUsers(page);
        //返回信息
        Map<String, List> listMap = new HashMap<>();
        listMap.put("commentDtoList",commentByPageDtoList);
        return new Result(listMap);
    }

    //根据帖子Id 查询帖子详情内容 查询成功后使文章浏览+1
    @Override
    public Result selectCommentByCommentId(CommentVo commentVo) {
        //查询内容
        List<CommentByPageDto> commentByPageDtoList = commentMapper.selectCommentByCommentIdAndUsers(commentVo.getUserId(), commentVo.getCommentId());
        //返回内容
        Map<String,List> commentDtoList = new HashMap<>();
        commentDtoList.put("commentDtoList",commentByPageDtoList);
        //文章浏览+1
        commentMapper.updateSawUp(commentVo.getCommentId());
        return new Result(19,"查询详情内容成功",commentDtoList);
    }

    @Override
    public Result commentWithOutAddressQianZhi(Integer userId,
                                               String commentTitle,
                                               String commentText,
                                               String commentCategoryName,
                                               String token)
    {
        //检查登录状态
        User user = userService.checkToken(token);
        if (user == null){
            return new Result("登录失效");
        }
        //设置信息
        long startTime = System.currentTimeMillis();
        Timestamp now1 = new Timestamp(startTime);
        Comment comment = new Comment();
        comment.setComments("0");
        comment.setCommentTitle(commentTitle);
        comment.setLikes("0");
        comment.setCommentTime(now1);
        comment.setSaw("0");
        comment.setUserId(userId);
        comment.setCommentText(commentText);
        Integer categoryId = commentCategoryMapper.selectCategoryIdByCategoryName(commentCategoryName);
        comment.setCommentCategoryId(categoryId);
        //插入数据库
        commentMapper.insert(comment);

        return new Result(18,"发帖成功");

    }


}
