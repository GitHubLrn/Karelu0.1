package com.kmust.Karelu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Chat对象", description="")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "chatid", type = IdType.AUTO)
    private Integer chatid;

    private Integer usersentid;

    private Integer usergetid;

    @TableField("createTime")
    private String createTime;

    private Integer bedone;

    private String chatthing;


}
