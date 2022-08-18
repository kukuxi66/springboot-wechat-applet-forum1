package com.xcx.service;

import com.xcx.dao.vo.CommentCategoryListVo;
import com.xcx.utils.Result;

public interface CommentCategoryService {

    Result selectAllCommentCategoryNameAndCounts();

    Result selectCommentByCommentCategoryAndPage(CommentCategoryListVo commentCategoryListVo);
}
