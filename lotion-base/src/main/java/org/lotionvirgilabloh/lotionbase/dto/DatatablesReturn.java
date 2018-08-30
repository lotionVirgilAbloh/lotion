package org.lotionvirgilabloh.lotionbase.dto;

import java.io.Serializable;
import java.util.List;

public class DatatablesReturn<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1289669936363867894L;

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
