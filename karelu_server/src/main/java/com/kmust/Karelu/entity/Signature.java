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
@ApiModel(value="Signature对象", description="")
public class Signature implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "signatureid", type = IdType.AUTO)
    private Integer signatureid;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "签名")
    private String signature;


}
