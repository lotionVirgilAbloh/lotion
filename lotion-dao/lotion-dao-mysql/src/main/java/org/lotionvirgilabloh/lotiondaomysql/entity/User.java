package org.lotionvirgilabloh.lotiondaomysql.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    public User(String username, String userpwd) {
        this.username = username;
        this.userpwd = userpwd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long userId;
    @Column(name="username")
    private String username;
    @Column(name = "userpwd")
    private String userpwd;
    @Column(name = "roles")
    @Type(type = "org.lotionvirgilabloh.lotiondaomysql.entity.StringSetType")
    private Set<String> roles =new LinkedHashSet<> ();


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}