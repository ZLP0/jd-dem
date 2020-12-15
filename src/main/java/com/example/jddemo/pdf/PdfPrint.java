package com.example.jddemo.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class PdfPrint {



    private static Logger logger = LoggerFactory.getLogger(PdfPrint.class);

    private Rectangle pageSize;
    private float marginLeft;
    private float marginRight;
    private float marginTop;
    private float marginBottom;
    private PdfWriter pdfWriter;
    private Document document;
    private OutputStream outputStream;


    /**
     * 默认A4 纸,如需其他尺寸,请使用带参构造
     */
    public PdfPrint() {
        this.pageSize = PageSize.A4;
        this.marginLeft = 10;
        this.marginRight = 10;
        this.marginTop = 40;
        this.marginBottom = 40;
    }

    public PdfPrint(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom) {
        this.pageSize = pageSize;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
    }

    public void open(OutputStream outputStream) throws DocumentException {
        this.outputStream=outputStream;
        document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        pdfWriter = PdfWriter.getInstance(document, this.outputStream);
        document.open();
    }

    public void close() {
        if (null != document) {
            document.close();
        }
        if (null != pdfWriter) {
            pdfWriter.close();
        }
        if(null != outputStream){
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *  创建table 行 默认样式
     * @param contentList
     * @return
     * @throws DocumentException
     */
    public PdfPTable createRow(List<String> contentList) throws DocumentException {
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font rowFont = new Font(bfChinese, 11, Font.NORMAL);
        return createRow(rowFont, null, contentList);
    }

    /**
     *  创建 PdfPTable 行
     * @param font
     * @param contentList
     * @return
     * @throws DocumentException
     */
    public PdfPTable createRow(Font font, List<String> contentList) throws DocumentException {
        return createRow(font, null, contentList);
    }

    private PdfPTable createRow(Font font, BaseColor backgroundColor, List<String> contentList) throws DocumentException {
        int numColumns = contentList.size();
        float[] columnWidths = new float[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = 1f;
        }
        return createRow(font, backgroundColor, contentList, columnWidths);
    }

    private PdfPTable createRow(Font font, BaseColor backgroundColor, List<String> contentList, float[] columnWidths) throws DocumentException {
        if (CollectionUtils.isEmpty(contentList)) {
            return null;
        }

        int numColumns = contentList.size();
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        for (int i = 0; i < numColumns; i++) {
            String content = contentList.get(i);
            PdfPCell cell = createPdfPCell(content, font, backgroundColor);
            table.addCell(cell);
        }
        return table;
    }

    private PdfPCell createPdfPCell(String string, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(string, font));
        cell.setBorderColor(BaseColor.BLACK);
        if (null != backgroundColor) {
            cell.setBackgroundColor(backgroundColor);
        }
        cell.setPaddingBottom(4f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }


    /**
     * 文字转换为 pdf 文件
     *
     * @param text           要转化的文字
     * @return
     */
    public void appendContent(String text) {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font rowFont = new Font(bfChinese, 11, Font.NORMAL);
            document.add(new Paragraph(text, rowFont));
        } catch (Exception e) {
            logger.error("PdfPrint.textToPdf error", e);
        }
    }


    /**
     *  将html转换为pdf
     *
     * @param text              html字符串
     * @return
     */
    public void appendHtml(String text) {
        try {
            text = ("<p>"+text+"</p>");
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter,document, new ByteArrayInputStream(text.getBytes("UTF-8")),null, Charset.forName("UTF-8"), new AsianFontProvider());
        } catch (Throwable e) {
            logger.error("PdfPrint.htmlToPdf error",e);
        }
    }


    public Rectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(Rectangle pageSize) {
        this.pageSize = pageSize;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public PdfWriter getPdfWriter() {
        return pdfWriter;
    }

    public void setPdfWriter(PdfWriter pdfWriter) {
        this.pdfWriter = pdfWriter;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}




