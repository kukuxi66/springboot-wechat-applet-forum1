package com.xcx.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class CommentCategoryByPageDto {


    /**
     * 帖子Id
     */
    @TableId(value = "commentId", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 上传者Id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 帖子标题
     */
    @TableField("commentTitle")
    private String commentTitle;

    /**
     * 帖子内容
     */
    @TableField("commentText")
    private String commentText;

    /**
     * 上传时间
     */
    @TableField("commentTime")
    private Date commentTime;

    /**
     * 点赞人数
     */
    private String likes;

    /**
     * 多少人看过
     */
    private String saw;

    /**
     * 评论数
     */
    private String comments;

    /**
     * 评论类别Id
     */
    @TableField("commentCategoryId")
    private Integer commentCategoryId;

    private String username;

    private String avatar;

    private String commentCategoryName;

}
