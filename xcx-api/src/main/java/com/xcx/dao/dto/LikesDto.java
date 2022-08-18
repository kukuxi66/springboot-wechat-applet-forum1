package com.xcx.dao.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LikesDto {

    private Integer UserId;

    private Integer CommentId;

}
