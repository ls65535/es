package com.ls.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * Created by ls on 2019/9/9.
 */
@Data
public class BaseEntity implements Serializable {
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;

}
