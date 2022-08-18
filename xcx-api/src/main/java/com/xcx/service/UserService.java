package com.xcx.service;

import com.xcx.dao.pojo.User;
import com.xcx.dao.vo.EmailVo;
import com.xcx.dao.vo.UserVo;
import com.xcx.dao.vo.UserVo1;
import com.xcx.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User findUserByName(String username);

    User findUserByNameAndPassword(String username , String password);

    void save(User user);

    void update(User user);

    User checkToken(String token);

    String md5pwd(String password);

    //上传头像
    Result addUserPortrait(MultipartFile file , Integer id);

    //发送验证邮箱
    Result sendEmail(String email);

    //绑定邮箱
    Result bindingEmail(EmailVo email);

}
