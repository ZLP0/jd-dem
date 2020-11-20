package com.example.jddemo.excel.mergecell;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class MergeCell {


    public static void main(String[] args) {
        // 写法1
        String fileName = "D:\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板")
                .registerWriteHandler(new MyMergeStrategy())//自定义合并 单元格
                .doWrite(dataList());
    }

    /**
     * 创建表头
     * @return
     */
    private static List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    /**
     * 创建数据
     * @return
     */
    private static List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 3; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add("数字" + i);
            data.add("时间" + i);
            list.add(data);
        }
        return list;
    }
}
