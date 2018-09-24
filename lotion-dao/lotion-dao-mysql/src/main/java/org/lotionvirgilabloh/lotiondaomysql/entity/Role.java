package org.lotionvirgilabloh.lotiondaomysql.entity;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    public final static String role_enable="1";
    public final static String role_disable="0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    protected String rid;
    @Column(name = "COMMONNAME")
    protected String commonName;
    @Column(name="type")
    protected Integer type;
    @Column(name = "SUMMARY")
    protected String summary;
    @Column(name = "STATUS")
    protected String status;
    @Column(name = "LASTUPDATE")
    protected Long lastUpdate;


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
