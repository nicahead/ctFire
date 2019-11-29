package com.nic.ctFire.common.domain;

import java.io.Serializable;

public class TbData extends BaseDomain implements Serializable {

    private float voltage;  //电压
    private float current;  //电流
    private float temper;   //温度
    private String did;
    private Integer state;  //1-正常 2-异常

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getTemper() {
        return temper;
    }

    public void setTemper(float temper) {
        this.temper = temper;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
