package org.lotionvirgilabloh.lotiondaomysql.entity;


import javax.persistence.*;

@Entity
@Table(name = "perm")
public class Permission{

    public static final String TYPE_URL = "URL";
    public static final String TYPE_BUTTON = "BUTTON";
    public static final String TYPE_UDEF = "UDEF";


    protected Integer permId;
    protected String name;
    protected String resource;
    protected String type;
    protected Boolean anyRoles;
    protected String roles;
    protected Boolean authc;
    protected Boolean avaliable;
    protected Long lastUpdate;

    public Permission(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMID")
    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "RESOURCE")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "ANYROLES")
    public Boolean getAnyRoles() {
        return anyRoles;
    }

    public void setAnyRoles(Boolean anyRoles) {
        this.anyRoles = anyRoles;
    }
    @Column(name = "ROLES")
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    @Column(name = "AUTHC")
    public Boolean getAuthc() {
        return authc;
    }

    public void setAuthc(Boolean authc) {
        this.authc = authc;
    }
    @Column(name = "avaliable")
    public Boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }
    @Column(name = "LASTUPDATE")
    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
