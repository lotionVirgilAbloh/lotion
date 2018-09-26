package org.lotionvirgilabloh.lotiondaomysql.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "perm")
public class Permission{

    public static final String TYPE_URL = "URL";
    public static final String TYPE_BUTTON = "BUTTON";
    public static final String TYPE_UDEF = "UDEF";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMID")
    protected Integer permId;
    @Column(name = "NAME")
    protected String name;
    @Column(name = "RESOURCE")
    protected String resource;
    @Column(name = "TYPE")
    protected String type;
    @Column(name = "ANYROLES")
    protected Boolean anyRoles;
    @Column(name = "roles")
    @Type(type = "org.lotionvirgilabloh.lotiondaomysql.entity.StringSetType")
    protected Set<String> roles =new LinkedHashSet<> ();
    @Column(name = "AUTHC")
    protected Boolean authc;
    @Column(name = "avaliable")
    protected Boolean avaliable;
    @Column(name = "LASTUPDATE")
    protected Long lastUpdate;

    public Permission(){

    }

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAnyRoles() {
        return anyRoles;
    }

    public void setAnyRoles(Boolean anyRoles) {
        this.anyRoles = anyRoles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }




    public Boolean getAuthc() {
        return authc;
    }

    public void setAuthc(Boolean authc) {
        this.authc = authc;
    }

    public Boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
