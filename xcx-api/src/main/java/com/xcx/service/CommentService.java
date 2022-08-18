package com.xcx.service;

import com.xcx.dao.vo.CommentListVo;
import com.xcx.dao.vo.CommentVo;
import com.xcx.dao.vo.LikesVo2;
import com.xcx.utils.Result;

public interface CommentService {


    Result selectAllCommentByPage(CommentListVo commentListVo);

    Result selectCommentByCommentId(CommentVo commentVo);

    Result commentWithOutAddressQianZhi(Integer userId, String commentTitle, String commentText, String commentCategoryName, String token);
}
