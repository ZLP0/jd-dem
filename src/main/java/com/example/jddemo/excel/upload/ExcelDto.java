package com.example.jddemo.excel.upload;

import java.util.ArrayList;
import java.util.List;

public class ExcelDto {

    private List<List<String>> head=new ArrayList<>();

    private List<List<Object>> content=new ArrayList<>();

   private boolean  isHaveError;//默认false

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public List<List<Object>> getContent() {
        return content;
    }

    public void setContent(List<List<Object>> content) {
        this.content = content;
    }

    public boolean isHaveError() {
        return isHaveError;
    }

    public void setHaveError(boolean haveError) {
        isHaveError = haveError;
    }

    @Override
    public String toString() {
        return "ExcelDto{" +
                "head=" + head +
                ", content=" + content +
                ", isHaveError=" + isHaveError +
                '}';
    }
}
