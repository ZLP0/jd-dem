package com.example.jddemo.redis.limit;

/**
 * 程序员  by dell
 * time  2021-02-23
 **/

import com.example.jddemo.response.CommonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FangshuaController {

    @AccessLimit(seconds=10, maxCount=2, needLogin=true)
    @RequestMapping("/fangshua")
    @ResponseBody
    public CommonResponse<String> fangshua(){

        CommonResponse<String> response = new CommonResponse<String>();
        response.setMessage("成功");

        return response;

    }
}
