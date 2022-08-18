package com.xcx.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class CommentSon {

    @TableId(value = "commentSonId", type = IdType.AUTO)
    private Integer commentSonId;

    /**
     * 评论者Id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 父帖子Id
     */
    @TableField("commentId")
    private Integer commentId;

    /**
     * 评论
     */
    @TableField("commentSonText")
    private String commentSonText;

    /**
     * 评论时间
     */
    @TableField("commentSonDate")
    private Date commentSonDate;


}
