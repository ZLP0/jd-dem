package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.example.jddemo.excel.upload.excellistener.AbstractDataEasyExcelListener;
import com.example.jddemo.excel.upload.excellistener.ExcelDto;
import com.example.jddemo.excel.upload.excellistener.MyDataEasyExcelListener;

public class upload {

    public static void main(String[] args) {

//=======================上传文件并校验=====================================================================
        AbstractDataEasyExcelListener listener = new MyDataEasyExcelListener();
        EasyExcel.read("D:\\1606185114659.xlsx").sheet().headRowNumber(1)
                .registerReadListener(listener).doRead();
        ExcelDto excelDto = listener.getExcelDto();

        System.out.println(excelDto);

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
