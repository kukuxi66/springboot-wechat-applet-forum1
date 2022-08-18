package com.xcx.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentSonDto {

    private Integer userId;

    private String username;

    private String avatar;

    private String commentSonText;

    private Date commentSonDate;

}
