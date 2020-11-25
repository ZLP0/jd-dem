package com.example.jddemo.excel.upload.excellistener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractDataEasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    protected   ExcelDto        excelDto    = new ExcelDto();

    private     File            tempFile    = null;
    private     ExcelWriter     excelWriter = null;
    private     WriteSheet      writeSheet  = null;
    private     StringBuilder   msg         = null;
    private List<List<String>>  head        = new ArrayList<>();//生成动态表头

    public AbstractDataEasyExcelListener() {
        //生成临时文件
        try {
            tempFile = File.createTempFile("test", ".xlsx");
            writeSheet = EasyExcel.writerSheet("模板").sheetNo(0).build();
            //写入临时文件
            //excelWriter = EasyExcel.write(tempFile).head(headerror).build();
            excelWriter = EasyExcel.write("D:\\abc.xlsx").head(head).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

        List<String> list = new ArrayList<>(headMap.values());
        //初始化表头
        if(head.size()==0){
            head.add(new ArrayList<String>(Collections.singleton("错误列信息提示")));
            list.forEach(item->{
                head.add(new ArrayList<String>(Collections.singleton(item)));
            });
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            List<String> headI = this.head.get(i+1);
            headI.add(str);
        }
        excelDto.setHead(head);
    }

    @Override
    public void invoke(Map<Integer, String> row, AnalysisContext context) {
        List<Object> responseRow = new ArrayList<>(row.values());
        Integer rowIndex = context.readRowHolder().getRowIndex();
        System.out.println("读取行" + rowIndex + "数据");
        msg = new StringBuilder("");
        //校验数据
        this.validator(responseRow, msg);
        //saveData.add(row);
        responseRow.add(0, msg.toString());
        excelDto.getTmpContent().add(responseRow);
        //  测试异常
       /* if (rowIndex == 2) {
            int a = 1 / 0;
        }*/
        // 三千条数据 写入一次
        if (excelDto.getTmpContent().size() >= excelDto.getCount()) {
            this.save(excelDto);//数据落库
            excelWriter.write(excelDto.getTmpContent(), writeSheet);//写入新文件
            excelDto.getTmpContent().clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("数据读取完毕");
        this.save(excelDto);//数据落库
        excelWriter.write(excelDto.getTmpContent(), writeSheet);//写入新文件
        excelDto.getTmpContent().clear();
        this.saveFile(tempFile);
        // 千万别忘记finish 会帮忙关闭流
        if (excelWriter != null) {
            excelWriter.finish();
        }
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 千万别忘记finish 会帮忙关闭流
        if (excelWriter != null) {
            excelWriter.finish();
        }
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
        //手动抛出异常 读操作终止
        throw new RuntimeException("运行时异常:" + exception.getMessage());
    }

    /**
     * 保存附件到服务器
     * @param tempFile
     */
    protected abstract void saveFile(File tempFile);

    /**
     * 行数据校验
     * @param row
     * @param msg
     */
    protected abstract void validator(List<Object> row, StringBuilder msg);

    /**
     * 数据库操作  保存数据
     *
     * @param excelDto
     */
    protected abstract void save(ExcelDto excelDto);

    public ExcelDto getExcelDto() {
        return excelDto;
    }

    public void setExcelDto(ExcelDto excelDto) {
        this.excelDto = excelDto;
    }

}
