package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.example.jddemo.excel.upload.excellistener.AbstractEasyExcelDataListener;
import com.example.jddemo.excel.upload.excellistener.ExcelHelper;
import com.example.jddemo.excel.upload.excellistener.MyDataEasyExcelListener;
import com.example.jddemo.response.CommonResponse;

public class upload {

    public static void main(String[] args) {

        CommonResponse<String> response = new CommonResponse<>();
        response.setCode(CommonResponse.CODE_NORMAL);
        ExcelHelper excelHelper = null;
        //创建监听器
        AbstractEasyExcelDataListener listener = new MyDataEasyExcelListener()
                .buildFileName("文件名")
                .buildSheetName("shee名");

        try {
            listener.open();
            EasyExcel.read("D:\\1606185114659.xlsx").sheet().headRowNumber(1)
                    .registerReadListener(listener).doRead();
            excelHelper = listener.getExcelHelper();
            if (excelHelper.isHaveError()) {
                throw new RuntimeException("校验数据不通过 请更改数据后重新导入");
            }
            System.out.println(excelHelper);
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.setCode(CommonResponse.CODE_WARN);
            response.setMessage(e.getMessage());
            response.setData(excelHelper.getUrl());
        }finally {
            listener.close();
        }
    }

}
