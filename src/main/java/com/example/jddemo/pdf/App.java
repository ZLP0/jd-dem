package com.example.jddemo.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 程序员  by dell
 * time  2020-12-15
 **/

public class App {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\a.pdf");
        FileOutputStream fileInputStream = new FileOutputStream(file);
        PdfPrint print = new PdfPrint();
        try {
            print.open(fileInputStream);
            List<String> title = Arrays.asList("我是标题");
            List<String> list = Arrays.asList("你", "好", "啊");
            PdfPTable head = print.createRow(title);
            PdfPTable row = print.createRow(list);
            print.getDocument().add(head);
            print.getDocument().add(row);
            print.appendContent("我是文本内容");
            print.appendHtml("<a href='https://www.baidu.com/'>百度一下你就知道</a>");
            print.appendHtml("<div style='color:red'>我是div</div>");
            print.appendHtml("<table border='1'>\n" +
                    "  <tr>\n" +
                    "    <td>Montd</td>\n" +
                    "    <td>Savings</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>January</td>\n" +
                    "    <td>$100</td>\n" +
                    "  </tr>\n" +
                    "</table>");
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (print != null) {
                print.close(); //fileInputStream 也会一起关闭
            }
        }

    }
}
