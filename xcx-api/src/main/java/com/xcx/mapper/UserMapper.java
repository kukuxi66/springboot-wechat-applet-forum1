package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.dto.EmailDto;
import com.xcx.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void updateAvatar(Integer id , String avatar);

   void updateEmailByUserId(Integer userId, String email);

    List<EmailDto> selecrEmailByUserId(Integer userId);
}
