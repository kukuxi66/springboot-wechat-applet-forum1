package com.xcx.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CommentCategory {

    /**
     * 评论类别Id
     */
    @TableId(value = "commentCategoryId", type = IdType.AUTO)
    private Integer commentCategoryId;

    /**
     * 类别名称
     */
    @TableField("commentCategoryName")
    private String commentCategoryName;

}
