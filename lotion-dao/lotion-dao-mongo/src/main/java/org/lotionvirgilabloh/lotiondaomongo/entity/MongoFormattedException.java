package org.lotionvirgilabloh.lotiondaomongo.entity;

import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.springframework.data.annotation.Id;

import java.util.Random;

public class MongoFormattedException extends FormattedException {

    @Id
    public String uid;

    public MongoFormattedException() {
        super();
        super.setTimeMillis(System.currentTimeMillis());
    }


    public MongoFormattedException(String id, String projectName) {
        super();
        this.uid = id;
        Random r = new Random();
        super.setExceptionID(r.nextInt());
        super.setProject(projectName);
        super.setTimeMillis(System.currentTimeMillis());
    }

    public MongoFormattedException(FormattedException fe) {
        super(fe.getExceptionID(), fe.getTimeMillis(), fe.getMessage(), fe.getProject(), fe.getAdditionalProperties());
        this.uid = String.valueOf(fe.getExceptionID());
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
