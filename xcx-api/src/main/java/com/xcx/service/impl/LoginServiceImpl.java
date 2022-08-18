package com.xcx.service.impl;

import com.alibaba.fastjson.JSON;
import com.xcx.dao.dto.UserDto;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.UserCheckLoginVo;
import com.xcx.dao.vo.UserVo;
import com.xcx.service.LoginService;
import com.xcx.service.UserService;
import com.xcx.utils.JWTUtils;
import com.xcx.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 注册用户
     * @param userVo
     * @return
     */
    @Override
    public Result register(UserVo userVo) {
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return new Result(-1,"注册失败");
        }
        User selectUserName = userService.findUserByName(userVo.getUsername());
        if (selectUserName != null){
            return new Result(-1,"注册失败");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(userService.md5pwd(password));
        user.setEmail("请验证邮箱");
        user.setAvatar("http://rfvoof3xc.hn-bkt.clouddn.com/013d4c4f-cd4c-4b6d-9821-a4d9c6b734a3.png");
        userService.save(user);
        return new Result(1,"注册成功");
    }

    /**
     * 用户登录
     * @param userVo
     * @return
     */
    @Override
    public Result login(UserVo userVo) {
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return new Result(-2,"登录失败");
        }
        //密码md5加密
        String md5pwd = userService.md5pwd(password);
        //查找用户名和加密后的密码是否存在
        User selectUserName = userService.findUserByNameAndPassword(userVo.getUsername(),md5pwd);
        //用户名或和密码不存在
        if (selectUserName == null){
            return new Result(-2,"登录失败");
        }
        //设置返回的账号全部信息
        UserDto userDto = new UserDto();
        userDto.setId(selectUserName.getId());
        userDto.setUsername(userVo.getUsername());
        userDto.setPassword(userVo.getPassword());
        userDto.setAvatar(selectUserName.getAvatar());
        userDto.setEmail(selectUserName.getEmail());
        //使用JWT生成token，并设置token
        String token = JWTUtils.createToken(Long.valueOf(selectUserName.getId()));
        userDto.setToken(token);
        //将token保存到redis中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(userDto),1, TimeUnit.DAYS);
        return new Result(2,"登录成功",userDto);
    }

    @Override
    public Result logincheck(UserCheckLoginVo userCheckLoginVo) {
        String token = userCheckLoginVo.getToken();
        //检查token
        if (StringUtils.isBlank(token)){
            return new Result(-990,"登录失效");
        }
        //检查token
        User user = userService.checkToken(token);
        if (user == null){
            return new Result(-990,"登录失效");
        }
        //设置返回的账号全部信息
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setAvatar(user.getAvatar());
        userDto.setEmail(user.getEmail());
        return new Result(990,"登录成功",userDto);
    }


}
