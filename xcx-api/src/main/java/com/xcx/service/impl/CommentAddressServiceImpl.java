package com.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xcx.dao.pojo.CommentAddress;
import com.xcx.mapper.CommentAddressMapper;
import com.xcx.service.CommentAddressService;
import com.xcx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentAddressServiceImpl implements CommentAddressService {

    @Autowired
    private CommentAddressMapper commentAddressMapper;

    @Override
    public Result selectCommentAddressByCommentId() {
        LambdaQueryWrapper<CommentAddress> commentAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<CommentAddress> commentAddresses = commentAddressMapper.selectList(commentAddressLambdaQueryWrapper);
        return new Result(commentAddresses);
    }
}
