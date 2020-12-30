package com.example.jddemo.excel.upload.excellistener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractEasyExcelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    protected ExcelHelper excelHelper = null;

    private File tempFile = null;//临时文件
    private ExcelWriter excelWriter = null;
    private WriteSheet writeSheet = null;
    private StringBuilder msg = null;// 行数据校验 提示信息
    private List<List<String>> head =null;//生成动态表头

    public AbstractEasyExcelDataListener() {

    }
    public AbstractEasyExcelDataListener buildSheetName(String sheetName){
        excelHelper.setSheetName(sheetName);
        return this;
    }
    public AbstractEasyExcelDataListener buildFileName(String  fileName){
        excelHelper.setFileName(fileName);
        return this;
    }

    public void open() {
        //生成临时文件
        try {
            excelHelper = new ExcelHelper();
            head = new ArrayList<>();
            tempFile = File.createTempFile("tmp", ".xlsx");
            writeSheet = EasyExcel.writerSheet(StringUtils.isBlank(excelHelper.getSheetName())?"模板": excelHelper.getSheetName()).sheetNo(0).build();
            //写入临时文件
            //excelWriter = EasyExcel.write(tempFile).head(head).build();
            excelWriter = EasyExcel.write("D:\\abc.xlsx").head(head).build();
            excelHelper.setHead(head);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        List<String> list = new ArrayList<>(headMap.values());
        //初始化表头
        if (head.size() == 0) {
            head.add(new ArrayList<String>(Collections.singleton("错误列信息提示")));
            list.forEach(item -> {
                head.add(new ArrayList<String>(Collections.singleton(item)));
            });
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            List<String> headI = this.head.get(i + 1);
            headI.add(str);
        }
    }

    @Override
    public void invoke(Map<Integer, String> row, AnalysisContext context) {
        List<Object> responseRow = new ArrayList<>(row.values());
        Integer rowIndex = context.readRowHolder().getRowIndex();
        System.out.println("读取行" + rowIndex + "数据");
        msg = new StringBuilder("");
        //校验数据
        this.validator(responseRow, msg);
        responseRow.add(0, msg.toString());
        excelHelper.getTmpContent().add(responseRow);
        //保存全量数据  高并发或者数据量较大时不建议使用
        if (this.enableContent()) {
            excelHelper.getContent().add(responseRow);
        }
        // 三千条数据 写入一次
        if (excelHelper.getTmpContent().size() >= excelHelper.getCount()) {
            if (!enableContent()) {
                excelHelper.setContent(excelHelper.getTmpContent());
                this.save(excelHelper);//数据落库
            }
            excelWriter.write(excelHelper.getTmpContent(), writeSheet);//写入新文件
            excelHelper.getTmpContent().clear();
        }
    }

    /**
     * 返回 true  ExcelDto content 会保存excel 中全量数据
     * 返回 false ExcelDto content 不会保存数据
     *
     * @return
     */
    protected boolean enableContent() {
        return false;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("数据读取完毕");
        if (!enableContent()) {
            excelHelper.setContent(excelHelper.getTmpContent());
        }
        excelWriter.write(excelHelper.getTmpContent(), writeSheet);//写入新文件
        // 千万别忘记finish 会帮忙关闭流
        if (excelWriter != null) {
            excelWriter.finish();
        }
        this.save(excelHelper);//数据落库
        this.saveFile(tempFile);// 保存临时文件 到服务器
        excelHelper.getTmpContent().clear();//清除临时数据
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //手动抛出异常 读操作终止
        throw new RuntimeException("excel处理数据监听异常:" + exception.getMessage());
    }

    /**
     * 保存附件到服务器
     *
     * @param tempFile
     */
    protected void saveFile(File tempFile) {
        System.out.println("保存文件到文件服务器");
        this.excelHelper.setUrl("url://文件服务器地址");
    }

    public void close() {
        // 千万别忘记finish 会帮忙关闭流
        if (excelWriter != null) {
            excelWriter.finish();
        }
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }

    }

    /**
     * 行数据校验
     *
     * @param row
     * @param msg
     */
    protected abstract void validator(List<Object> row, StringBuilder msg);

    /**
     * 数据库操作  保存数据
     *
     * @param excelHelper
     */
    protected abstract void save(ExcelHelper excelHelper);

    public ExcelHelper getExcelHelper() {
        return excelHelper;
    }

    public void setExcelHelper(ExcelHelper excelHelper) {
        this.excelHelper = excelHelper;
    }

}
