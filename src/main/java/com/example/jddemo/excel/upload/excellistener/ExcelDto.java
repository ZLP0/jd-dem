package com.example.jddemo.excel.upload.excellistener;

import java.util.ArrayList;
import java.util.List;

public class ExcelDto {

    private List<List<String>> head = new ArrayList<>();//表头

    private List<List<Object>> tmpContent = new ArrayList<>();//临时内容

    private boolean isHaveError;//默认false

    private int count = 3000;//三千条数据 提交一次

    private String url;//文件上传后的地址

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public List<List<Object>> getTmpContent() {
        return tmpContent;
    }

    public void setTmpContent(List<List<Object>> tmpContent) {
        this.tmpContent = tmpContent;
    }

    public boolean isHaveError() {
        return isHaveError;
    }

    public void setHaveError(boolean haveError) {
        isHaveError = haveError;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ExcelDto{" +
                "head=" + head +
                ", content=" + tmpContent +
                ", isHaveError=" + isHaveError +
                '}';
    }
}
