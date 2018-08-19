package org.lotionvirgilabloh.lotiondaomongo.entity;

import org.lotionVirgilAbloh.lotionbase.dto.FormattedException;
import org.springframework.data.annotation.Id;

public class MongoFormattedException extends FormattedException {

    @Id
    public String uid;

    public MongoFormattedException() {
        super();
        super.setTimeMillis(System.currentTimeMillis());
    }


    public MongoFormattedException(String id ,String projectName){
        super();
        this.uid =id;
        super.setExceptionID(id);
        super.setProject(projectName);
        super.setTimeMillis(System.currentTimeMillis());
    }

    public MongoFormattedException(FormattedException fe){
        this.uid =fe.getExceptionID();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "MongoFormattedException{" +
                "uid='" + uid + '\'' +
                '}';
    }
}
