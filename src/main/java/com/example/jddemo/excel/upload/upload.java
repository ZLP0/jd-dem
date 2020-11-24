package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.*;

public class upload {

    public static void main(String[] args) {

//=======================上传文件并校验=====================================================================
        ExcelDto excelDto=new ExcelDto();// 有错误数据则  对应错误信息返回给前端
        EasyExcel.read("D:\\1606185114659.xlsx").sheet().headRowNumber(1)
                .registerReadListener(new DataEasyExcelListener(excelDto)).doRead();

        System.out.println(excelDto);
//===============导出错误文件=========后续优化 将数据写入临时文件 再删除==============================================================================
        if(excelDto.isHaveError()){
            if(CollectionUtils.isEmpty(excelDto.getContent()))
            {
                System.out.println("异常信息：：导出数据为空");
                return;
            }
            ExcelWriter excelWriter = null;
            String fileName = "D:\\" + System.currentTimeMillis() + ".xlsx";
            List<List<String>> head = excelDto.getHead();
            head.add(0, Arrays.asList("错误信息提示"));
           /* ExcelWriterSheetBuilder writerSheetBuilder = EasyExcel.write(fileName).head(head).sheet("模板");
            writerSheetBuilder.doWrite(excelDto.getContent());*/

           // 手动关闭流
            File tempFile=null;
            try {
                tempFile= File.createTempFile("testtest", ".xlsx");
                excelWriter = EasyExcel.write(tempFile).head(head).build();
                WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
                excelWriter.write(excelDto.getContent(), writeSheet);
                excelWriter.write(excelDto.getContent(), writeSheet);

                //tempFile  上传文件服务器

            }catch(Exception e){

            } finally {
                // 千万别忘记finish 会帮忙关闭流
                if (excelWriter != null) {
                    excelWriter.finish();
                }
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }


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
