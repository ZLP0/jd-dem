package com.example.jddemo.excel.cellstyle;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.jddemo.excel.mergecell.MyMergeStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class HeadContentCellStyle {

    public static void main(String[] args) {
        // 写法1
        String fileName = "D:\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板")
                .registerWriteHandler(new MyMergeStrategy())//自定义合并 单元格
                .registerWriteHandler(myHorizontalCellStyleStrategy())
                .doWrite(dataList());
    }
    /**
     * 创建表头
     * @return
     */
    private static List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串");
        head0.add("二级表头");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    /**
     * 创建数据
     * @return
     */
    private static List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 5; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add("数字" + i);
            data.add("时间" + i);
            list.add(data);
        }
        return list;
    }

    /**
     *  设置表头  和内容样式
     * @return
     */
    private static HorizontalCellStyleStrategy myHorizontalCellStyleStrategy(){
//1 表头样式策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //表头前景设置淡蓝色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short)12);
        headWriteCellStyle.setWriteFont(headWriteFont);

        //内容样式  多个样式则隔行换色
        List<WriteCellStyle>   listCntWritCellSty =  new ArrayList<>();

//2 内容样式策略  样式一
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        //内容字体大小
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short)11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 头默认了 FillPatternType所以可以不指定。
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        //设置背景黄色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        //设置水平靠左
        //contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        //设置边框样式
        setBorderStyle(contentWriteCellStyle);
        //内容风格可以定义多个。
        listCntWritCellSty.add(contentWriteCellStyle);

//2 内容样式策略  样式二
        WriteCellStyle contentWriteCellStyle2 = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色。
        // 头默认了 FillPatternType所以可以不指定。
        contentWriteCellStyle2.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle2.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        //设置垂直居中
        contentWriteCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框样式
        setBorderStyle(contentWriteCellStyle2);
        listCntWritCellSty.add(contentWriteCellStyle2);
        // 水平单元格风格综合策略(表头 + 内容)
        // return  new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return  new HorizontalCellStyleStrategy(headWriteCellStyle, listCntWritCellSty);
    }

    /**
     * 设置边框样式
     * @param contentWriteCellStyle
     */
    private static void setBorderStyle(WriteCellStyle contentWriteCellStyle){
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        // contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex()); //颜色
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    }

}
