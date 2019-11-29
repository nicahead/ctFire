package com.nic.ctFire.common.domain;

import java.io.Serializable;
import java.util.Date;

public class TbUser extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 7602993320798295801L;
    private String username;
    private String password;
    private String email;
    private Integer role;
    private Integer state;
    private Date logined;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLogined() {
        return logined;
    }

    public void setLogined(Date logined) {
        this.logined = logined;
    }
}
