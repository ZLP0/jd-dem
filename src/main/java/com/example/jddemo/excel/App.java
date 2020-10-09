package com.example.jddemo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class App {

    @GetMapping("download2")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = "test";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(data());


    }

    public static void main(String[] args) {

        // 写法1
        String fileName = "D:\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板")
                .registerWriteHandler(new MyMergeStrategy())//自定义合并 单元格
                .doWrite(dataList());

    }

    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }


    public static void simpleWrite() {
        String fileName = "D:\\test1.xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        List<DemoData> data = data();
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data);
    }


    private static List<List<String>> head() {

        List<List<String>> list = new ArrayList<List<String>>();

        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        head0.add("第二行");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        head1.add("数字2");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        head2.add("日期2");
        list.add(head0);
        list.add(head1);
        list.add(head2);



        return list;
    }

    private static List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 1; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            list.add(data);
        }
        return list;
    }




    public static class MyMergeStrategy extends AbstractMergeStrategy {



        //合并坐标集合
        private List<CellRangeAddress> cellRangeAddresss;

        //构造
        public MyMergeStrategy() {

            List<CellRangeAddress> list = new ArrayList<>();
            //合并第4行
            CellRangeAddress item1 = new CellRangeAddress(2, 2, 0, 3);

            list.add(item1);


            this.cellRangeAddresss = list;
        }

        /**
         * merge
         *
         * @param sheet
         * @param cell
         * @param head
         * @param relativeRowIndex
         */
        @Override
        protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
            //合并单元格
            /**
             *  ****加个判断:if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {}****
             * 保证每个cell被合并一次，如果不加上面的判断，因为是一个cell一个cell操作的，
             * 例如合并A2:A3,当cell为A2时，合并A2,A3，但是当cell为A3时，又是合并A2,A3，
             * 但此时A2,A3已经是合并的单元格了
             */
            for (CellRangeAddress item : cellRangeAddresss) {
                sheet.addMergedRegionUnsafe(item);
            }

           /* if (CollectionUtils.isNotEmpty(cellRangeAddresss)) {
                if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
                    for (CellRangeAddress item : cellRangeAddresss) {
                        sheet.addMergedRegionUnsafe(item);
                    }
                }
            }*/
        }
    }
}
