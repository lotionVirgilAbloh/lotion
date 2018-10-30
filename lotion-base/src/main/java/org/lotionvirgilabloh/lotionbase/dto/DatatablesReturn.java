package org.lotionvirgilabloh.lotionbase.dto;

import java.io.Serializable;
import java.util.List;

public class DatatablesReturn<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1289669936363867894L;


    /**
     * { "draw": 1, "recordsTotal": 57, "recordsFiltered": 57, "data": [ { }]}
     */
    // 获取Datatables发送的参数 必要
    // 定义查询数据总记录数
    // 条件过滤后记录数 必要recordsFiltered
    // 表的总记录数 必要 recordsTotal
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
