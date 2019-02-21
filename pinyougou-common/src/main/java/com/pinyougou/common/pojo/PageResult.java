package com.pinyougou.common.pojo;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private Long totalNum;
    private List<T> list;

    public PageResult(Long totalNum, List<T> list) {
        this.totalNum = totalNum;
        this.list = list;
    }

    public PageResult() {
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
