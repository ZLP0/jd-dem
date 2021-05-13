package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.example.jddemo.excel.upload.excellistener.AbstractEasyExcelDataListener;
import com.example.jddemo.excel.upload.excellistener.ExcelHelper;
import com.example.jddemo.excel.upload.excellistener.MyDataEasyExcelListener;
import com.example.jddemo.response.CommonResponse;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/
@Controller
public class AppController {

    @Resource
    private ObjectFactory<MyDataEasyExcelListener> obj;

   @RequestMapping(value = "/excel")
   public  void exce(String file){
       AbstractEasyExcelDataListener listener=obj.getObject();

       System.out.println("对象:"+listener);

       CommonResponse<String> response = new CommonResponse<>();
       response.setCode(CommonResponse.CODE_SUCCESS);
       ExcelHelper excelHelper = null;

       try {
           listener.open();//1606185114659.xlsx
           listener.buildFileName("文件名")
                   .buildSheetName("shee名");
           EasyExcel.read("D:\\"+file).sheet().headRowNumber(1)
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
