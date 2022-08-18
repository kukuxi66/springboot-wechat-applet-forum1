package com.xcx.service;

import com.xcx.dao.vo.UserNewPasswordVo;
import com.xcx.dao.vo.UserVo1;
import com.xcx.utils.Result;

public interface UserPowerService {

    Result changeUserName(UserVo1 userVo1);

    Result changePassword(UserNewPasswordVo userNewPasswordVo);
}
