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
 * @since 2021-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Danmu对象", description="")
public class Danmu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "danmuid", type = IdType.AUTO)
    private Integer danmuid;

    private Integer uid;

    private String danmu;

    private String icon;


}
