package com.xcx.controller;

import com.xcx.dao.vo.UserCheckLoginVo;
import com.xcx.dao.vo.UserVo;
import com.xcx.service.LoginService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody UserVo userVo){
        return loginService.login(userVo);
    }

    @PostMapping("register")
    public Result register(@RequestBody UserVo userVo){
        return loginService.register(userVo);
    }

    @PostMapping("logincheck")
    public Result logincheck(@RequestBody UserCheckLoginVo userCheckLoginVo) {
        return loginService.logincheck(userCheckLoginVo);
    }

}
