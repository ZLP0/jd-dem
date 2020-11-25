package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.example.jddemo.excel.upload.excellistener.AbstractDataEasyExcelListener;
import com.example.jddemo.excel.upload.excellistener.ExcelDto;
import com.example.jddemo.excel.upload.excellistener.MyDataEasyExcelListener;

public class upload {

    public static void main(String[] args) {

       //创建监听器
        AbstractDataEasyExcelListener listener = new MyDataEasyExcelListener();
        EasyExcel.read("D:\\1606185114659.xlsx").sheet().headRowNumber(1)
                .registerReadListener(listener).doRead();
        ExcelDto excelDto = listener.getExcelDto();

        System.out.println(excelDto);

    }

}
