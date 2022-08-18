package com.xcx.service;

import com.xcx.dao.vo.UserCheckLoginVo;
import com.xcx.dao.vo.UserVo;
import com.xcx.utils.Result;

public interface LoginService {

    Result register(UserVo userVo);

    Result login(UserVo userVo);

    Result logincheck(UserCheckLoginVo userCheckLoginVo);
}
