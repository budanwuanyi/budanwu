package com.bdw.web.controller.order;


import com.bdw.common.core.controller.BaseController;
import com.bdw.common.core.domain.AjaxResult;
import com.bdw.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhenghongkai
 * @since 2022-11-15
 */
@RestController
@Api(tags = "订单")
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation("推送订单")
    @PostMapping("/sendOrder")
    public AjaxResult sendOrder() throws Exception {
        orderService.sendOrder();
        return AjaxResult.success();
    }


}
