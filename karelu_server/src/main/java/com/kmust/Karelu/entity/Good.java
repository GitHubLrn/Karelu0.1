package com.kmust.Karelu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Good对象", description="")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "点赞记录")
    @TableId(value = "goodid", type = IdType.AUTO)
    private Integer goodid;

    @ApiModelProperty(value = "点赞人id")
    private Integer userid;

    @ApiModelProperty(value = "被点赞id")
    private Integer noteid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createDate")
    private String createDate;


}
