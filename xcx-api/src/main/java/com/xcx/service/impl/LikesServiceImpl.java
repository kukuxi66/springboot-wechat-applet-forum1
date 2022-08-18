package com.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xcx.dao.dto.CommentLikesDto;
import com.xcx.dao.dto.LikesDto;
import com.xcx.dao.pojo.Comment;
import com.xcx.dao.pojo.CommentLikes;
import com.xcx.dao.pojo.Likes;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.LikesVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.mapper.ArticleMapper;
import com.xcx.mapper.CommentLikesMapper;
import com.xcx.mapper.CommentMapper;
import com.xcx.mapper.LikesMapper;
import com.xcx.service.LikesService;
import com.xcx.service.UserService;
import com.xcx.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentLikesMapper commentLikesMapper;

    @Autowired
    private CommentMapper commentMapper;

    //查询该用户是否对该文章点赞
    @Override
    public Result selectArticleLike(Integer articleId, Integer userId) {
        LambdaQueryWrapper<Likes> likesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        likesLambdaQueryWrapper.eq(Likes::getUserId,userId);
        likesLambdaQueryWrapper.eq(Likes::getCommentId,articleId);
        List<Likes> likesList = likesMapper.selectList(likesLambdaQueryWrapper);
        if (likesList.isEmpty()){
            return new Result(-999,"未点赞");
        }
        List<LikesDto> likesDtoList = likescopyList(likesList);
        return new Result(999,"已点赞",likesDtoList);
    }

    //按下点赞按钮
    @Override
    public Result articleLikes(LikesVo likesVo) {
        //检查登录状态
        User user = userService.checkToken(likesVo.getToken());
        if (user == null){
            return new Result("登录失效");
        }
        Result likeResult = selectArticleLike(likesVo.getArticleId(), likesVo.getUserId());
        Integer likeCode = likeResult.getCode();
        if (likeCode == -999){
            //未点赞，进行点赞操作
            Likes likes = new Likes();
            likes.setUserId(likesVo.getUserId());
            likes.setCommentId(likesVo.getArticleId());
            likesMapper.insert(likes);
            ////点赞成功后，使文章总赞数+1
            articleMapper.updateLikesUp(likesVo.getArticleId());
            return new Result(11,"点赞成功");
        }
        if (likeCode == 999){
            //已点赞，进行取消点赞操作
            LambdaQueryWrapper<Likes> likesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            likesLambdaQueryWrapper.eq(Likes::getUserId,likesVo.getUserId());
            likesLambdaQueryWrapper.eq(Likes::getCommentId,likesVo.getArticleId());
            likesMapper.delete(likesLambdaQueryWrapper);
            ////点赞取消后，使文章总赞数-1
            articleMapper.updateLikesDown(likesVo.getArticleId());
            return new Result(10,"取消点赞成功");
        }
        return null;
    }

    //帖子点赞
    @Override
    public Result likes(LikesVo2 likesVo2) {

        //检查登录状态
        User user = userService.checkToken(likesVo2.getToken());
        if (user == null){
            return new Result("登录失效");
        }
        Result likeResult = selectCommentLikes(likesVo2);
        Integer likeCode = likeResult.getCode();
        if (likeCode == -999) {
            //未点赞，进行点赞操作
            CommentLikes commentLikes = new CommentLikes();
            commentLikes.setUserId(likesVo2.getUserId());
            commentLikes.setCommentId(likesVo2.getCommentId());
            commentLikesMapper.insert(commentLikes);
            ////点赞成功后，使文章总赞数+1
            commentMapper.updateCommentLikesUp(likesVo2.getCommentId());
            //查询点赞成功后的赞数
            List<CommentLikesDto> commentLikesDtos = commentMapper.selectLikesByCommentId(likesVo2.getCommentId());
            return new Result(11,"点赞成功",commentLikesDtos);
        }
        if (likeCode == 999) {
            //已点赞，进行取消点赞操作
            LambdaQueryWrapper<CommentLikes> commentLikesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            commentLikesLambdaQueryWrapper.eq(CommentLikes::getCommentId,likesVo2.getCommentId());
            commentLikesLambdaQueryWrapper.eq(CommentLikes::getUserId,likesVo2.getUserId());
            commentLikesMapper.delete(commentLikesLambdaQueryWrapper);
            ////点赞成功后，使文章总赞数-1
            commentMapper.updateCommentLikesDown(likesVo2.getCommentId());
            //查询点赞成功后的赞数
            List<CommentLikesDto> commentLikesDtos = commentMapper.selectLikesByCommentId(likesVo2.getCommentId());
            return new Result(10,"取消点赞成功",commentLikesDtos);
        }
        return null;
    }

    //查询该用户是否对该帖子点过赞
    @Override
    public Result selectCommentLikes(LikesVo2 likesVo2) {

        LambdaQueryWrapper<CommentLikes> likesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        likesLambdaQueryWrapper.eq(CommentLikes::getUserId,likesVo2.getUserId());
        likesLambdaQueryWrapper.eq(CommentLikes::getCommentId,likesVo2.getCommentId());
        List<CommentLikes> likesList = commentLikesMapper.selectList(likesLambdaQueryWrapper);
        if (likesList.isEmpty()){
            return new Result(-999,"未点赞");
        }
        List<LikesDto> likesDtoList = commentLikesCopyList(likesList);
        return new Result(999,"已点赞",likesDtoList);

    }

    //文章
    private LikesDto likescopy(Likes likes){
        LikesDto likesDto = new LikesDto();
        BeanUtils.copyProperties(likes,likesDto);
        return likesDto;
    }

    //文章
    private List<LikesDto> likescopyList(List<Likes> likes){
        List<LikesDto> likesDtoList = new ArrayList<>();
        for (Likes like : likes) {
            likesDtoList.add(likescopy(like));
        }
        return likesDtoList;
    }

    //帖子
    private LikesDto commentLikesCopy(CommentLikes likes){
        LikesDto likesDto = new LikesDto();
        BeanUtils.copyProperties(likes,likesDto);
        return likesDto;
    }

    //帖子
    private List<LikesDto> commentLikesCopyList(List<CommentLikes> likes){
        List<LikesDto> likesDtoList = new ArrayList<>();
        for (CommentLikes like : likes) {
            likesDtoList.add(commentLikesCopy(like));
        }
        return likesDtoList;
    }

}
