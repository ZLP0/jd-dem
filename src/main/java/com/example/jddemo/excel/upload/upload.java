package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.example.jddemo.excel.upload.excellistener.AbstractEasyExcelDataListener;
import com.example.jddemo.excel.upload.excellistener.ExcelDto;
import com.example.jddemo.excel.upload.excellistener.MyDataEasyExcelListener;
import com.example.jddemo.response.ResponseEntity;

public class upload {

    public static void main(String[] args) {

        ResponseEntity<String> response = new ResponseEntity<>();
        response.setCode(ResponseEntity.CODE_NORMAL);
        ExcelDto excelDto = null;
        //创建监听器
        AbstractEasyExcelDataListener listener = new MyDataEasyExcelListener();
        try {

            listener.open();
            EasyExcel.read("D:\\1606185114659.xlsx").sheet().headRowNumber(1)
                    .registerReadListener(listener).doRead();
            excelDto = listener.getExcelDto();
            if (excelDto.isHaveError()) {
                throw new RuntimeException("校验数据不通过 请更改数据后重新导入");
            }
            System.out.println(excelDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.setCode(ResponseEntity.CODE_WARN);
            response.setMessage(e.getMessage());
            response.setData(excelDto.getUrl());
        }finally {
            listener.close();
        }
    }

}
