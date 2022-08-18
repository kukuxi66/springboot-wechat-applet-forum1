package com.xcx.service.impl;

import com.xcx.dao.dto.CommentSonDto;
import com.xcx.dao.pojo.CommentSon;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.CommentSonVo;
import com.xcx.dao.vo.CommentVo2;
import com.xcx.mapper.CommentMapper;
import com.xcx.mapper.CommentSonMapper;
import com.xcx.service.CommentSonService;
import com.xcx.service.UserService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentSonServiceImpl implements CommentSonService {

    @Autowired
    private CommentSonMapper commentSonMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result selectCommentSonByCommentId(CommentSonVo commentSonVo) {
        List<CommentSonDto> commentSonDtos = commentSonMapper.selectCommentSonByCommentId(commentSonVo.getCommentId());
        Map<String,List> commentSonDtoList = new HashMap<>();
        commentSonDtoList.put("commentSonDtoList",commentSonDtos);
        return new Result(14,"子评论查询成功",commentSonDtoList);
    }

    //评论
    @Override
    public Result commentSon(CommentVo2 commentVo2) {
        //检查登录状态
        User user = userService.checkToken(commentVo2.getToken());
        if (user == null){
            return new Result("登录失效");
        }
        //插入评论信息到数据库
        long startTime = System.currentTimeMillis();
        Timestamp now1 = new Timestamp(startTime);
        CommentSon  commentSon = new CommentSon();
        commentSon.setCommentId(commentVo2.getCommentId());
        commentSon.setCommentSonText(commentVo2.getCommentSonText());
        commentSon.setUserId(commentVo2.getUserId());
        commentSon.setCommentSonDate(now1);
        commentSonMapper.insert(commentSon);
        //查询新的批评
        CommentSonVo commentSonVo = new CommentSonVo();
        commentSonVo.setCommentId(commentVo2.getCommentId());
        //设置返回信息
        Result result = selectCommentSonByCommentId(commentSonVo);
        //评论成功 评论数+1
        commentMapper.updateCommentsUp(commentVo2.getCommentId());
        return new Result(8,"评论成功",result);
    }


}
