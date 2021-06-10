package com.kmust.Karelu.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Karelu
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Note对象", description="")
public class NoteShow implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动态分享id")
    private Integer noteid;

    @ApiModelProperty(value = "用户name")
    private String username;

    @ApiModelProperty(value = "用户头像")
    private String icon;

    @ApiModelProperty(value = "图片")
    private String[] images;

    @ApiModelProperty(value = "文章")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private String date;

    @ApiModelProperty(value = "点赞数")
    private Integer goodcount;

    @ApiModelProperty(value = "是否已点赞")
    private String beGooded;

    @ApiModelProperty(value = "评论区")
    private String commentChoice;

    @ApiModelProperty(value = "评论数")
    private int commentCount;

    @ApiModelProperty(value = "评论")
    private List<CommentShow> comment;


}
