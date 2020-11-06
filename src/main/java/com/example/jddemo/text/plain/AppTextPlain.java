package com.example.jddemo.text.plain;

import com.alibaba.fastjson.JSON;
import com.example.jddemo.copy.Person;
import com.example.jddemo.manager.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @Classname AppTextPlain
 * @Description
 * @Author ext.zhaolupeng
 * @Date 2020/11/5
 * @Version 1.0
 **/

@Controller
public class AppTextPlain {


    /**
     * context-Type: text/plain
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/plain")
    @ResponseBody
    public void test(  HttpServletRequest request ) throws IOException {

        BufferedReader reader =  request.getReader();
        char[] buf = new char[512];
            int len = 0;
            StringBuffer contentBuffer = new StringBuffer();
            while (true) {
                if (!((len = reader.read(buf)) != -1)) break;
                contentBuffer.append(buf, 0, len);
            }
            System.out.println(  contentBuffer.toString());

    }

    public static <T> T fetchPostByTextPlain(HttpServletRequest request, Class<T> clazz) {
        try {
            BufferedReader reader = request.getReader();
            char[] buf = new char[512];
            int len = 0;
            StringBuffer contentBuffer = new StringBuffer();
            while ((len = reader.read(buf)) != -1) {
                contentBuffer.append(buf, 0, len);
            }
            return JSON.parseObject(contentBuffer.toString(), clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
