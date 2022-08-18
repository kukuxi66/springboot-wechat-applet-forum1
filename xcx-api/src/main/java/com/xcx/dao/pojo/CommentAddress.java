package com.xcx.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CommentAddress {

    /**
     * Id
     */
    @TableId(value = "addressId", type = IdType.AUTO)
    private Integer addressId;


    /**
     * 帖子ID
     */
    @TableField("commentId")
    private Integer commentId;


    /**
     * 帖子图片
     */
    @TableField("commentAddress")
    private String commentAddress;


}
