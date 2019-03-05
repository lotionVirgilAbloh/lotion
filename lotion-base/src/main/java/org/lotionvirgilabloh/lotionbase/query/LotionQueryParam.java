package org.lotionvirgilabloh.lotionbase.query;

import java.io.Serializable;


public class LotionQueryParam implements Serializable {


    boolean isGetRedis;
    boolean isParaGet;
    boolean isGetAndForget;
    boolean isStoreRedis;
    boolean storeMin;


    public boolean isGetRedis() {
        return isGetRedis;
    }

    public void setGetRedis(boolean getRedis) {
        isGetRedis = getRedis;
    }

    public boolean isParaGet() {
        return isParaGet;
    }

    public void setParaGet(boolean paraGet) {
        isParaGet = paraGet;
    }

    public boolean isGetAndForget() {
        return isGetAndForget;
    }

    public void setGetAndForget(boolean getAndForget) {
        isGetAndForget = getAndForget;
    }

    public boolean isStoreRedis() {
        return isStoreRedis;
    }

    public void setStoreRedis(boolean storeRedis) {
        isStoreRedis = storeRedis;
    }

    public boolean isStoreMin() {
        return storeMin;
    }

    public void setStoreMin(boolean storeMin) {
        this.storeMin = storeMin;
    }
}
