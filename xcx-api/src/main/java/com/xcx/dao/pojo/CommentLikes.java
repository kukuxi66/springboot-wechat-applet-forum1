package com.xcx.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CommentLikes {


    @TableId(value = "likesId" , type = IdType.AUTO)
    private Integer likesId;

    @TableField("userId")
    private Integer userId;

    @TableField("commentId")
    private Integer commentId;

}
