package com.example.jddemo.excel.upload;

import java.util.List;

public class ExcelDto {

    private List<List<String>> head;

    private List<List<Object>> content;

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
}
