package com.bdw.order.service;

import com.bdw.common.core.domain.AjaxResult;
import com.bdw.order.entity.OrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhenghongkai
 * @since 2022-11-15
 */
public interface IOrderService extends IService<OrderEntity> {

     void sendOrder() throws Exception;
}
