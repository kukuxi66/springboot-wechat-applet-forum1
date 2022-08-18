package com.xcx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcx.dao.dto.CommentByPageDto;
import com.xcx.dao.pojo.CommentAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentAddressMapper extends BaseMapper<CommentAddress> {

}
