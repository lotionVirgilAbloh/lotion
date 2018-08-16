package org.lotionVirgilAbloh.lotionbase.auth;

import java.util.Set;

public class LotionUser {

    private String userName;

    private String password;

    private int status=1;


    private Set<String> roleSet ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }
    public int isActive(){
        return status;
    }
}
