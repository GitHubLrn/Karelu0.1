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
 * @since 2021-04-23
 */
@Data
public class AdminUser{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "是否启用：1启用，0禁用")
    private String state;
    private String icon;

    private String signature;


}
