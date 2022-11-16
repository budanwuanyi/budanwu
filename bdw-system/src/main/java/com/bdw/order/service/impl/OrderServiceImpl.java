package com.bdw.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdw.common.core.domain.AjaxResult;
import com.bdw.order.service.IOrderService;
import com.bdw.order.entity.OrderEntity;
import com.bdw.order.mapper.OrderMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhenghongkai
 * @since 2022-11-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void sendOrder() throws Exception {
        List<OrderEntity> orders = orderMapper.selectList(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getStatus, 0));
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,JSON.toJSON(orders).toString() );
        Request request = new Request.Builder()
                .url("https://dev-api.jushuitan.com/open/jushuitan/orders/upload")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        orders.stream().forEach(order->{
            order.setStatus(1);
            orderMapper.updateById(order);
        });
    }
}
