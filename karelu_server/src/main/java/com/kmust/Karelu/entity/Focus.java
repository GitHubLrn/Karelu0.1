package com.kmust.Karelu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Focus对象", description="")
public class Focus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关注号")
    @TableId(value = "focusid", type = IdType.AUTO)
    private Integer focusid;

    @ApiModelProperty(value = "关注人")
    private Integer userid;

    @ApiModelProperty(value = "被关注人")
    private Integer focusedid;


}
