package com.xcx.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Article {

    @TableId(value = "articleId", type = IdType.AUTO)
    private Integer articleId;

    @TableField("userId")
    private Integer userId;

    @TableField("articleTitle")
    private String articleTitle;

    @TableField("articleText")
    private String articleText;

    @TableField("articleTime")
    private Date articleTime;

    private String likes;

    private String saw;

    /**
     * 文章首页展示图片地址
     */
    @TableField("articleAddress")
    private String articleAddress;

}
