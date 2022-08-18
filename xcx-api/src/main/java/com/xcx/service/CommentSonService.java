package com.xcx.service;

import com.xcx.dao.vo.CommentSonVo;
import com.xcx.dao.vo.CommentVo2;
import com.xcx.utils.Result;

public interface CommentSonService {
    Result selectCommentSonByCommentId(CommentSonVo commentSonVo);

    Result commentSon(CommentVo2 commentVo2);
}
