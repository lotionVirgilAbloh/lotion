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

    protected String commonName;

    protected Integer type;

    protected String summary;

    protected String status;

    protected Long lastUpdate;

    public Role() {
    }
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
    @Column(name = "COMMONNAME")
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
    @Column(name="type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "SUMMARY")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "LASTUPDATE")
    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
