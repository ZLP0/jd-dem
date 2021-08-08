package com.example.jddemo.redis.limit;

/**
 * 程序员  by dell
 * time  2021-02-23
 **/

import com.example.jddemo.response.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FangshuaController {

    @AccessLimit(seconds=10, maxCount=2, needLogin=true)
    @RequestMapping("/fangshua")
    @ResponseBody
    public ApiResponse<String> fangshua(){

        ApiResponse<String> response = new ApiResponse<String>();
        response.setMessage("成功");

        return response;

    }
}
