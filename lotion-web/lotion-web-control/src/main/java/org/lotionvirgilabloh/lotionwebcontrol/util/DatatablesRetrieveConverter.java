package org.lotionvirgilabloh.lotionwebcontrol.util;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesRetrieve;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

public class DatatablesRetrieveConverter {

    public static DatatablesRetrieve convert(HttpServletRequest request) throws IOException {
        DatatablesRetrieve datatablesRetrieve = new DatatablesRetrieve();

        //获取datatables request中的Map，由于是顺序存储的，因此可以遍历
        Map<String, String[]> map = request.getParameterMap();

        //定义变量
        int draw = 0;
        LinkedList<DatatablesRetrieve.DatatablesRetrieveColumns> columns = new LinkedList<>();
        LinkedList<DatatablesRetrieve.DatatablesRetrieveOrder> order = new LinkedList<>();
        int start = 0;
        int length = 0;
        DatatablesRetrieve.DatatablesRetrieveSearch search = datatablesRetrieve.new DatatablesRetrieveSearch();
        long underline = 0;

        //临时变量
        DatatablesRetrieve.DatatablesRetrieveColumns tempColumns = datatablesRetrieve.new DatatablesRetrieveColumns();
        DatatablesRetrieve.DatatablesRetrieveOrder tempOrder = datatablesRetrieve.new DatatablesRetrieveOrder();
        DatatablesRetrieve.DatatablesRetrieveSearch tempSearch = datatablesRetrieve.new DatatablesRetrieveSearch();

        //顺序遍历
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            if (entry.getKey().equals("draw")) {
                if (entry.getValue()[0] != null) {
                    //获取draw
                    draw = Integer.parseInt(entry.getValue()[0]);
                } else {
                    throw new IOException();
                }
            } else if (entry.getKey().startsWith("columns")) {
                String lastBracket = (entry.getKey().substring(entry.getKey().lastIndexOf("[") + 1, entry.getKey().lastIndexOf("]")));
                if (lastBracket.equals("data")) {
                    //一次columns遍历开始
                    tempColumns = datatablesRetrieve.new DatatablesRetrieveColumns();
                    tempColumns.setData(entry.getValue()[0]);
                } else if (lastBracket.equals("name")) {
                    tempColumns.setName(entry.getValue()[0]);
                } else if (lastBracket.equals("searchable")) {
                    if (entry.getValue()[0] != null) {
                        tempColumns.setSearchable(Boolean.parseBoolean(entry.getValue()[0]));
                    } else {
                        throw new IOException();
                    }
                } else if (lastBracket.equals("orderable")) {
                    if (entry.getValue()[0] != null) {
                        tempColumns.setOrderable(Boolean.parseBoolean(entry.getValue()[0]));
                    } else {
                        throw new IOException();
                    }
                } else if (lastBracket.equals("value")) {
                    tempSearch = datatablesRetrieve.new DatatablesRetrieveSearch();
                    tempSearch.setValue(entry.getValue()[0]);
                } else if (lastBracket.equals("regex")) {
                    //一次columns遍历结束
                    if (entry.getValue()[0] != null) {
                        tempSearch.setRegex(Boolean.parseBoolean(entry.getValue()[0]));
                    } else {
                        throw new IOException();
                    }
                    tempColumns.setSearch(tempSearch);
                    columns.add(tempColumns);
                }
            } else if (entry.getKey().startsWith("order")) {
                String lastBracket = (entry.getKey().substring(entry.getKey().lastIndexOf("[") + 1, entry.getKey().lastIndexOf("]")));
                if (lastBracket.equals("column")) {
                    //一次order遍历开始
                    tempOrder = datatablesRetrieve.new DatatablesRetrieveOrder();
                    if (entry.getValue()[0] != null) {
                        tempOrder.setColumn(Integer.parseInt(entry.getValue()[0]));
                    } else {
                        throw new IOException();
                    }
                } else if (lastBracket.equals("dir")) {
                    //一次order遍历结束
                    tempOrder.setDir(entry.getValue()[0]);
                    order.add(tempOrder);
                }
            } else if (entry.getKey().equals("start")) {
                if (entry.getValue()[0] != null) {
                    start = Integer.parseInt(entry.getValue()[0]);
                } else {
                    throw new IOException();
                }
            } else if (entry.getKey().equals("length")) {
                if (entry.getValue()[0] != null) {
                    length = Integer.parseInt(entry.getValue()[0]);
                } else {
                    throw new IOException();
                }
            } else if (entry.getKey().startsWith("search")) {
                String lastBracket = (entry.getKey().substring(entry.getKey().lastIndexOf("[") + 1, entry.getKey().lastIndexOf("]")));
                if (lastBracket.equals("value")) {
                    tempSearch = datatablesRetrieve.new DatatablesRetrieveSearch();
                    tempSearch.setValue(entry.getValue()[0]);
                } else if (lastBracket.equals("regex")) {
                    if (entry.getValue()[0] != null) {
                        tempSearch.setRegex(Boolean.parseBoolean(entry.getValue()[0]));
                    } else {
                        throw new IOException();
                    }
                }
            } else if (entry.getKey().equals("_")) {
                if (entry.getValue()[0] != null) {
                    underline = Long.parseLong(entry.getValue()[0]);
                } else {
                    throw new IOException();
                }
            }
        }

        datatablesRetrieve.setDraw(draw);
        datatablesRetrieve.setColumns(columns);
        datatablesRetrieve.setOrder(order);
        datatablesRetrieve.setStart(start);
        datatablesRetrieve.setLength(length);
        datatablesRetrieve.setSearch(search);
        datatablesRetrieve.setUnderline(underline);

        return datatablesRetrieve;
    }

}
