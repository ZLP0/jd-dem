package com.example.jddemo.excelThread;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@Controller
public class demoController {

    volatile int count = -1;

    // 10w条 数据需要 5秒
    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        long start = System.currentTimeMillis();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        new Thread(() -> {
            long startThread1 = System.currentTimeMillis();
            System.out.println("================[线程1 ]开始执行====================");

            System.out.println("线程1  开始写入数据");

            WriteSheet sheet1 = EasyExcel.writerSheet(count++, "线程模板1").head(DemoData.class).build();
            excelWriter.write(data(), sheet1);

            System.out.println("线程1 导出数据完毕");
            countDownLatch.countDown();

            long endThread1 = System.currentTimeMillis();
            System.out.println(data().size() + "条数据 [线程1]耗时：" + (endThread1 - startThread1) / 1000 + "秒");
        }).start();

        new Thread(() -> {
            long startThread1 = System.currentTimeMillis();
            System.out.println("================[线程2]开始执行====================");

            System.out.println("线程2  开始写入数据");

            WriteSheet sheet1 = EasyExcel.writerSheet(count++, "线程模板1").head(DemoData.class).build();
            excelWriter.write(data(), sheet1);

            System.out.println("线程1 导出数据完毕");
            countDownLatch.countDown();

            long endThread1 = System.currentTimeMillis();
            System.out.println(data().size() + "条数据 [线程2]耗时：" + (endThread1 - startThread1) / 1000 + "秒");
        }).start();




        countDownLatch.await();
        excelWriter.finish();
        long end = System.currentTimeMillis();
        System.out.println(data().size() + "条数据 总共 耗时：" + (end - start) / 1000 + "秒");
        System.out.println("执行结束");

    }


    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        DemoData data = null;
        for (int i = 0; i < 10000; i++) {
            data = new DemoData();
            data.setStr1("字符串" + System.currentTimeMillis());
            data.setStr2("字符串" + i);
            data.setStr3("字符串" + i);
            data.setStr4("字符串" + i);
            data.setStr5("字符串" + i);
            data.setStr6("字符串" + i);
            data.setStr7("字符串" + i);
            data.setStr8("字符串" + i);
            data.setStr9("字符串" + i);
            data.setStr10("字符串" + i);
            list.add(data);
        }
        return list;
    }

}
