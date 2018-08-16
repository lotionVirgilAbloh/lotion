package org.lotionVirgilAbloh.lotionbase.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseMapDTO implements Serializable {

    private static final long serialVersionUID = 5238091498471853720L;

    private Map<String, Object> msg;

    public BaseMapDTO() {
        this.msg = new HashMap<String, Object>();
    }

    public BaseMapDTO(Map<String, Object> msg) {
        this.msg = msg;
    }

    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        StringBuilder bufString = new StringBuilder("BaseMapDTO{msg={");
        for (Map.Entry entry : msg.entrySet()) {
            bufString.append("{key=");
            bufString.append(entry.getKey());
            bufString.append(",value=");
            bufString.append(entry.getValue().toString());
            bufString.append("},");
        }
        bufString.setLength(bufString.length() - 1);
        bufString.append("}}");
        return bufString.toString();
    }
}