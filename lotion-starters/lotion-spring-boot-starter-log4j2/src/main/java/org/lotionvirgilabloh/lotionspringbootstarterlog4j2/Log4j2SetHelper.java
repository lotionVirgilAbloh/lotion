package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import java.util.Map;

public class Log4j2SetHelper {
    private String key;
    private Map<String, String> setter;

    public Log4j2SetHelper(String key, Map<String, String> setter) {
        this.key = key;
        this.setter = setter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getSetter() {
        return setter;
    }

    public void setSetter(Map<String, String> setter) {
        this.setter = setter;
    }
}
