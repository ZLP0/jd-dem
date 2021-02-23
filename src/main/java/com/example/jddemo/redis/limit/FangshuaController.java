package com.example.jddemo.redis.limit;

/**
 * 程序员  by dell
 * time  2021-02-23
 **/

import com.example.jddemo.response.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FangshuaController {

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping("/fangshua")
    @ResponseBody
    public ResponseEntity<String> fangshua(){

        ResponseEntity<String> response = new ResponseEntity<String>();
        response.setMessage("成功");

        return response;

    }
}
