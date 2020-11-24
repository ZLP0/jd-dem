package com.example.jddemo.excel.upload;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class DataEasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    private ExcelDto excelDto;

    public DataEasyExcelListener() {
    }

    public DataEasyExcelListener(ExcelDto excelDto) {
        this.excelDto = excelDto;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        ArrayList<List<String>> head = new ArrayList<>();
        headMap.values().forEach(obj -> {
            head.add(Arrays.asList(obj));
        });
        excelDto.setHead(head);
    }

    @Override
    public void invoke(Map<Integer, String> row, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        List<Object> responseRow = new ArrayList<>(row.values());
        StringBuilder msg = new StringBuilder("");
        // 数据校验
        String number = row.get(1);
        if (!isNumber(number, 2)) {
            System.out.println(number + "不是数字");
            msg.append(number + "不是数字");
            excelDto.setHaveError(true);
        }
        //校验 。。。。。。。。。
        if(true){

        }
        System.out.println("读取行" + rowIndex + "数据");
        //saveData.add(row);
        responseRow.add(0, msg.toString());
        excelDto.getContent().add(responseRow);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("数据读取完毕");
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
