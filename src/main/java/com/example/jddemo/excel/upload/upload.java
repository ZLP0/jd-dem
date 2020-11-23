package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class upload {

    public static void main(String[] args) {
         Map<Integer, String> head = new HashMap<>();
        List<Map<Integer, String>> data = new LinkedList<>();

        EasyExcel.read("D:\\1605858201435.xlsx").sheet()
                .registerReadListener(new AnalysisEventListener<Map<Integer, String>>() {

                    // easy-excel   默认把第一行当做表头
                    @Override
                    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                        head.putAll(headMap);
                    }


                    @Override
                    public void invoke(Map<Integer, String> row, AnalysisContext context) {

                        Integer rowIndex = context.readRowHolder().getRowIndex();
                        //默认从第二行开始读取数据
                        // 如果第二行也是表头 则不处理
                        if(rowIndex<=1)
                        {
                            return;
                        }
                        System.out.println("读取行"+rowIndex+"数据");
                        data.add(row);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("读取文件成功,一共:{"+data.size()+"}行......");
                    }
                }).doRead();
        System.out.println("文件头："+head);
        System.out.println("数据："+data);
    }
}
