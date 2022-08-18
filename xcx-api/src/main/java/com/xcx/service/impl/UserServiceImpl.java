package com.xcx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xcx.dao.dto.EmailDto;
import com.xcx.dao.dto.UserDto;
import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.EmailVo;
import com.xcx.dao.vo.UserVo;
import com.xcx.dao.vo.UserVo1;
import com.xcx.mapper.UserMapper;
import com.xcx.service.UserService;
import com.xcx.utils.JWTUtils;
import com.xcx.utils.QiniuUtils;
import com.xcx.utils.Result;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private QiniuUtils qiniuUtils;

    @Resource
    JavaMailSenderImpl mailSender;

    String checkCode;

    //加密盐
    private static final String slat = "xcx--!!";

    /**
     * 查找用户名是否存在
     * @param username
     * @return
     */
    public User findUserByName(String username){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        userLambdaQueryWrapper.last("limit 1");
        return userMapper.selectOne(userLambdaQueryWrapper);
    }

    /**
     * 查找用户名和密码是否存在
     * @param username
     * @param password
     * @return
     */
    public User findUserByNameAndPassword(String username , String password){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        userLambdaQueryWrapper.eq(User::getPassword,password);
        userLambdaQueryWrapper.last("limit 1");
        return userMapper.selectOne(userLambdaQueryWrapper);
    }

    /**
     * 插入一条用户数据
     * @param user
     */
    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    /**
     * 按ID更新用户名
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User checkToken(String token) {

        //查询token是否为空
        if (StringUtils.isBlank(token)){
            return null;
        }
        //查询token是否存在redis
        Map<String, Object> ResultToken = JWTUtils.checkToken(token);
        if (ResultToken == null){
            return null;
        }
        //从redis获取token
        String RedisCheckToken = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(RedisCheckToken)){
            return null;
        }
        //根据token存入用户信息
        User user = JSON.parseObject(RedisCheckToken,User.class);
        return user;


    }

    /**
     * 将密码转变为MD5加密
     * @param password
     * @return
     */
    @Override
    public String md5pwd(String password) {

        return DigestUtils.md5Hex(password + slat);

    }

    //上传头像
    @Override
    public Result addUserPortrait(MultipartFile file , Integer id) {

        //上传图片至七牛云
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        //上传成功
        if (upload){
            //修改数据库
            String avatar = QiniuUtils.url + fileName;
            userMapper.updateAvatar(id,avatar);
            //上传成功 返回图片地址
            return new Result(6,"上传成功", avatar);
        }
        return new Result("上传失败");


    }

    @Override
    public Result sendEmail(String email) {
        checkCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成六位随机数
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");
        message.setText("邮箱验证码是：" + checkCode);
        message.setTo(email);
        message.setFrom("2936293625@qq.com");
        mailSender.send(message);
        System.out.println(email);
        return new Result(5, "发送验证码成功",checkCode);
    }

    @Override
    public Result bindingEmail(EmailVo email) {
        if (StringUtils.isEmpty(email.getEmail()) || StringUtils.isEmpty(email.getMsg())){
            return new Result(-4,"验证码错误");
        }
        //检查登录状态
        User user = checkToken(email.getToken());
        if (user == null){
            return new Result(-4,"登录失效");
        }
        if (!email.getMsg().equals(checkCode)){
            System.out.println("生成" + checkCode);
            System.out.println("获取" + email.getMsg());
            return new Result(-4,"验证码错误");
        }

        //查询邮箱是否存在
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail,email.getEmail());
        User selectUserEmail = userMapper.selectOne(userLambdaQueryWrapper);
        if (selectUserEmail != null){
            return new Result(-11,"该邮箱已被绑定");
        }
        //更新邮箱
        userMapper.updateEmailByUserId(email.getUserId(),email.getEmail());
        //查询邮箱
        List<EmailDto> emailDtos = userMapper.selecrEmailByUserId(email.getUserId());
        return new Result(4,"绑定成功",emailDtos);
    }

    public static void main(String[] args) {
        System.out.println( DigestUtils.md5Hex("a123456789" + slat));
    }

}
