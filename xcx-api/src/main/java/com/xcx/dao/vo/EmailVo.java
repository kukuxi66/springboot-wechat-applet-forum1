package com.xcx.dao.vo;

import lombok.Data;

@Data
public class EmailVo {

    private Integer userId;

    private String token;

    private String email;

    private String msg;


}
