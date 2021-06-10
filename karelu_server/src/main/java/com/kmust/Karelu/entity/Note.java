package com.kmust.Karelu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Karelu
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Note对象", description="")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动态分享id")
    @TableId(value = "noteid", type = IdType.AUTO)
    private Integer noteid;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "图片")
    private String images;

    @ApiModelProperty(value = "文章")
    private String note;

    @ApiModelProperty(value = "发布时间")
    @TableField("createDate")
    private String createDate;


}
