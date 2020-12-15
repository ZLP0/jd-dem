package com.example.jddemo.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 * 程序员  by dell
 * time  2020-12-15
 **/

class AsianFontProvider extends XMLWorkerFontProvider {


    public Font getFont(final String fontName, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, size, style, color);
        font.setColor(color);
        return font;
    }

}
