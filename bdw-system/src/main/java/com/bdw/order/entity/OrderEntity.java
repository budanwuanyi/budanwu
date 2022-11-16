package com.bdw.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhenghongkai
 * @since 2022-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order")
@ApiModel(value="OrderEntity对象", description="")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long merId;

    private Long goodsId;

    private Long customerId;

    private Long orderDetailsId;

    private BigDecimal orderAmount;

    private BigDecimal actualAmount;

    private int status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime orderFinishTime;




}
