package com.xcx.controller;

import com.xcx.dao.vo.EmailVo;
import com.xcx.service.UserService;
import com.xcx.utils.QiniuUtils;
import com.xcx.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //上传头像
    @PostMapping("addUserPortrait")
    public Result addUserPortrait(@RequestParam("file") MultipartFile file,
                                  @RequestParam Integer id
    ){

        return userService.addUserPortrait(file,id);

    }

    //发送验证邮箱
    @GetMapping("sendEmail")
    public Result sendEmail(@RequestParam String email){
        return userService.sendEmail(email);
    }

    //绑定邮箱
    @PostMapping("bindingEmail")
    public Result bindingEmail(@RequestBody EmailVo email){
        return userService.bindingEmail(email);
    }
}
