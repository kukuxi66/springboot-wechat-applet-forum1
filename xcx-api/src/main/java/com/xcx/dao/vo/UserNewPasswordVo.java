package com.xcx.dao.vo;

import lombok.Data;

@Data
public class UserNewPasswordVo {

    private Integer id;

    private String newpassword;

    private String token;
}
