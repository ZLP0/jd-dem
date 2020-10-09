package com.example.jddemo.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sentinel")
public class SentinelController {


    @SentinelResource(value = "/test")
    @RequestMapping(value = "test")
    public String test()
    {
        return " success";
    }



}
