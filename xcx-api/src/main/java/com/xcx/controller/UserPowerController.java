package com.xcx.controller;

import com.xcx.dao.vo.UserNewPasswordVo;
import com.xcx.dao.vo.UserVo1;
import com.xcx.service.UserPowerService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPowerController {

    @Autowired
    private UserPowerService userPowerService;

    @PostMapping("changeUserName")
    public Result changeUserName(@RequestBody UserVo1 userVo1){
        return userPowerService.changeUserName(userVo1);
    }


    @PostMapping("changePassword")
    public Result changePassword(@RequestBody UserNewPasswordVo userNewPasswordVo){
        return userPowerService.changePassword(userNewPasswordVo);
    }


}
