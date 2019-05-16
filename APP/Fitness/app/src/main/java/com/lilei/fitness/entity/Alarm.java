package com.lilei.fitness.entity;

public class Alarm {
    private int alarmID;
    private String alarmtime;
    private String alarmtype;

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public void setAlarmtime(String alarmtime) {
        this.alarmtime = alarmtime;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public void setAlarmmiddle(String alarmmiddle) {
        this.alarmmiddle = alarmmiddle;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public String getAlarmtime() {
        return alarmtime;
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public String getAlarmmiddle() {
        return alarmmiddle;
    }

    private String alarmmiddle;


}
