package org.lotionvirgilabloh.lotionbase.dto;

import java.io.Serializable;
import java.util.List;

public class DatatablesRetrieve implements Serializable {

    private static final long serialVersionUID = -8107170883889572381L;

    private int draw;

    private List<DatatablesRetrieveColumns> columns;

    private List<DatatablesRetrieveOrder> order;

    private int start;

    private int length;

    private DatatablesRetrieveSearch search;

    private long underline;

    public DatatablesRetrieve() {
    }

    public DatatablesRetrieve(int draw, List<DatatablesRetrieveColumns> columns, List<DatatablesRetrieveOrder> order, int start, int length, DatatablesRetrieveSearch search, long underline) {
        this.draw = draw;
        this.columns = columns;
        this.order = order;
        this.start = start;
        this.length = length;
        this.search = search;
        this.underline = underline;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<DatatablesRetrieveColumns> getColumns() {
        return columns;
    }

    public void setColumns(List<DatatablesRetrieveColumns> columns) {
        this.columns = columns;
    }

    public List<DatatablesRetrieveOrder> getOrder() {
        return order;
    }

    public void setOrder(List<DatatablesRetrieveOrder> order) {
        this.order = order;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DatatablesRetrieveSearch getSearch() {
        return search;
    }

    public void setSearch(DatatablesRetrieveSearch search) {
        this.search = search;
    }

    public long getUnderline() {
        return underline;
    }

    public void setUnderline(long underline) {
        this.underline = underline;
    }

    @Override
    public String toString() {
        return "DatatablesRetrieve{" +
                "draw=" + draw +
                ", columns=" + columns +
                ", order=" + order +
                ", start=" + start +
                ", length=" + length +
                ", search=" + search +
                ", underline=" + underline +
                '}';
    }

    public static class DatatablesRetrieveOrder implements Serializable{

        private static final long serialVersionUID = 8714337559506472425L;

        private int column;

        private String dir;

        public DatatablesRetrieveOrder() {
        }

        public DatatablesRetrieveOrder(int column, String dir) {
            this.column = column;
            this.dir = dir;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "DatatablesRetrieveOrder{" +
                    "column=" + column +
                    ", dir='" + dir + '\'' +
                    '}';
        }
    }

    public static class DatatablesRetrieveColumns implements Serializable{

        private static final long serialVersionUID = 3327616272989718612L;

        private String data;

        private String name;

        private boolean searchable;

        private boolean orderable;

        private DatatablesRetrieveSearch search;

        public DatatablesRetrieveColumns() {
        }

        public DatatablesRetrieveColumns(String data, String name, boolean searchable, boolean orderable, DatatablesRetrieveSearch search) {
            this.data = data;
            this.name = name;
            this.searchable = searchable;
            this.orderable = orderable;
            this.search = search;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public void setSearchable(boolean searchable) {
            this.searchable = searchable;
        }

        public boolean isOrderable() {
            return orderable;
        }

        public void setOrderable(boolean orderable) {
            this.orderable = orderable;
        }

        public DatatablesRetrieveSearch getSearch() {
            return search;
        }

        public void setSearch(DatatablesRetrieveSearch search) {
            this.search = search;
        }

        @Override
        public String toString() {
            return "DatatablesRetrieveColumns{" +
                    "data='" + data + '\'' +
                    ", name='" + name + '\'' +
                    ", searchable=" + searchable +
                    ", orderable=" + orderable +
                    ", search=" + search +
                    '}';
        }
    }

    public static class DatatablesRetrieveSearch implements Serializable {

        private static final long serialVersionUID = 4382366860190613828L;

        private String value;

        private boolean regex;

        public DatatablesRetrieveSearch() {
        }

        public DatatablesRetrieveSearch(String value, boolean regex) {
            this.value = value;
            this.regex = regex;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isRegex() {
            return regex;
        }

        public void setRegex(boolean regex) {
            this.regex = regex;
        }

        @Override
        public String toString() {
            return "DatatablesRetrieveSearch{" +
                    "value='" + value + '\'' +
                    ", regex=" + regex +
                    '}';
        }
    }

}
