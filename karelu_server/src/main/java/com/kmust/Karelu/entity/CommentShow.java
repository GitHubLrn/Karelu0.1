package com.kmust.Karelu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentShow {

    private Integer commentid;

    private Integer noteid;

    private Integer userid;

    private String createdate;

    private String commentthing;

    private String iconrul;

    private String nickname;

    @ApiModelProperty(value = "评论展开")
    private String seeReply;
}
