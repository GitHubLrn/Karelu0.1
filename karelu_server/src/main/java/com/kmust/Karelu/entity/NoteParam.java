package com.kmust.Karelu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
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
 * @since 2021-04-23
 */
@Data
public class NoteParam {

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "文章")
    private String note;

    @ApiModelProperty(value = "上传的图片")
    private String imagesUrl;

    @ApiModelProperty(value = "删除的图片")
    private String deleteImg;
}
