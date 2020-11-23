package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import org.apache.commons.lang3.StringUtils;

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

                        //字符串表头	数字表头	日期表头
                        //              表头2
                        //"字符串0"	   "数字0"	         "时间0"
                        //"字符串1"	   "数字1"	         "时间1"
                        //"字符串2"	   "数字2"	         "时间2"
                        if(rowIndex<=1)
                        {
                            return;
                        }
                        // 数据校验
                        String number = row.get(1);
                        if(!isNumber(number,2)){
                            System.out.println(number+"不是数字");
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



    /**
     * 数字类型  最多保留两位小数
     *
     * @param str   校验的字符串
     * @param scale 保留小数位
     * @return
     */
    public static boolean isNumber(String str, int scale) {
        String reg = "^(\\d{1,8})(\\.\\d{1," + scale + "})?$";
        return str.matches(reg);

    }
}
