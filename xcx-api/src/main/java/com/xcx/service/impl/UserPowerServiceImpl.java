package com.xcx.service.impl;

import com.xcx.dao.dto.UserDto;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.UserNewPasswordVo;
import com.xcx.dao.vo.UserVo1;
import com.xcx.service.UserPowerService;
import com.xcx.service.UserService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPowerServiceImpl implements UserPowerService {

    @Autowired
    private UserService userService;

    /**
     * 修改用户名
     * @param userVo1
     * @return
     */
    @Override
    public Result changeUserName(UserVo1 userVo1) {
        //检查用户名
        User userByName = userService.findUserByName(userVo1.getNewUsername());
        if (userByName != null){
            return new Result(-13,"用户已存在");
        }
        //检查token
        User user = userService.checkToken(userVo1.getToken());
        if (user == null){
            return new Result(-13,"登录失效");
        }
        //修改数据库
        User user1 = new User();
        user1.setUsername(userVo1.getNewUsername());
        user1.setId(userVo1.getId());
        userService.update(user1);
        //返回给前端
        UserDto userDto = new UserDto();
        userDto.setUsername(userVo1.getNewUsername());
        return new Result(25,"修改成功",userDto.getUsername());
    }

    @Override
    public Result changePassword(UserNewPasswordVo userNewPasswordVo) {
        //检查token
        User user = userService.checkToken(userNewPasswordVo.getToken());
        if (user == null){
            return new Result("登录失效");
        }
        //修改数据库
        User user1 = new User();
        user1.setPassword(userService.md5pwd(userNewPasswordVo.getNewpassword()));
        user1.setId(userNewPasswordVo.getId());
        userService.update(user1);
        return new Result(20,"修改成功");
    }
}
