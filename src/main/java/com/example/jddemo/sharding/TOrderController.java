package com.example.jddemo.sharding;

import com.example.jddemo.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * (TOrder)表控制层
 *
 * @author makejava
 * @since 2021-08-08 14:33:04
 */
@RestController
@RequestMapping("tOrder")
public class TOrderController {


    @Resource
    TOrderDao tOrderDao;

    @GetMapping("selectOne")
    public ApiResponse selectOne() {

        TOrder tOrder = new TOrder();
        int id = new Random().nextInt(1000);
        tOrder.setUserId(id);
        tOrderDao.insert(tOrder);
        return ApiResponse.buildSuccess("success", tOrder);
    }

    @GetMapping("query")
    public ApiResponse query() {

        TOrder tOrder = new TOrder();

        List<TOrder> tOrders = tOrderDao.queryAll(tOrder);
        return ApiResponse.buildSuccess("success", tOrder);
    }


}
