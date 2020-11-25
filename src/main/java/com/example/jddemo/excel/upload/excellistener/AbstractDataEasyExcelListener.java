package com.example.jddemo.excel.upload.excellistener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public  abstract class AbstractDataEasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    protected ExcelDto excelDto=new ExcelDto();
    private File tempFile = null;
    private ExcelWriter excelWriter = null;
    private WriteSheet writeSheet = null;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        List<List<String>> head = new ArrayList<>();
        headMap.values().forEach(obj -> {
            head.add(Arrays.asList(obj));
        });
        excelDto.setHead(head);
        //生成临时文件
        try {
            tempFile = File.createTempFile("test", ".xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> headerror = new ArrayList<>();
        headerror.add(Arrays.asList("错误列信息提示"));
        headerror.addAll(head);
        writeSheet = EasyExcel.writerSheet("模板").sheetNo(0).build();
        //写入临时文件
        //excelWriter = EasyExcel.write(tempFile).head(headerror).build();
        excelWriter = EasyExcel.write("D:\\abc.xlsx").head(headerror).build();
    }

    @Override
    public void invoke(Map<Integer, String> row, AnalysisContext context) {
        List<Object> responseRow = new ArrayList<>(row.values());
        Integer rowIndex = context.readRowHolder().getRowIndex();
        StringBuilder msg = new StringBuilder("");

        this.validator(responseRow,msg);

        System.out.println("读取行" + rowIndex + "数据");
        //saveData.add(row);
        responseRow.add(0, msg.toString());
        excelDto.getTmpContent().add(responseRow);

        // 三千次 写入一次
        if(excelDto.getTmpContent().size()>=excelDto.getCount()){
            this.save(excelDto);//数据落库
            excelWriter.write(excelDto.getTmpContent(), writeSheet);//写入新文件
            excelDto.getTmpContent().clear();
        }
    }

    protected abstract void validator(List<Object> row, StringBuilder msg);

    /**
     * 数据库操作  保存数据
     * @param excelDto
     */
    protected abstract void save(ExcelDto excelDto);

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

    protected abstract void saveFile(File tempFile);

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 千万别忘记finish 会帮忙关闭流
        if (excelWriter != null) {
            excelWriter.finish();
        }
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    public ExcelDto getExcelDto() {
        return excelDto;
    }

    public void setExcelDto(ExcelDto excelDto) {
        this.excelDto = excelDto;
    }

}
