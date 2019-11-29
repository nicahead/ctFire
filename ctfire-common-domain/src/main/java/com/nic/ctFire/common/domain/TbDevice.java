package com.nic.ctFire.common.domain;

import java.io.Serializable;

public class TbDevice extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -8866076579655851118L;
    private String name;
    private String server;
    private Integer port;
    private String located;
    private Integer state;
    private Float maxVoltage;  //电压最大值
    private Float minVoltage;  //电压最小值
    private Float maxCurrent;  //电流最大值
    private Float minCurrent;  //电流最小值
    private Float maxTemper;  //温度最大值
    private Float minTemper;  //温度最小值
    private Integer switc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Float getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(Float maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public Float getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(Float minVoltage) {
        this.minVoltage = minVoltage;
    }

    public Float getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(Float maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public Float getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(Float minCurrent) {
        this.minCurrent = minCurrent;
    }

    public Float getMaxTemper() {
        return maxTemper;
    }

    public void setMaxTemper(Float maxTemper) {
        this.maxTemper = maxTemper;
    }

    public Float getMinTemper() {
        return minTemper;
    }

    public void setMinTemper(Float minTemper) {
        this.minTemper = minTemper;
    }

    public Integer getSwitc() {
        return switc;
    }

    public void setSwitc(Integer switc) {
        this.switc = switc;
    }
}
