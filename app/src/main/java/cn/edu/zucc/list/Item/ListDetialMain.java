package cn.edu.zucc.list.Item;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ListDetialMain {
    private String detialname;
    private boolean ischeck;
    private int listnum;
    private int _id;

    public int get_id(){return _id;}
    public void set_id(int _id){this._id = _id;}

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    private String deadline;

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    private String ps;

    public String getOwn() {
        return own;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    private String own;

    public ListDetialMain (String detialname){
        this.detialname = detialname;
    }
    public ListDetialMain (){
        this.detialname = null;
        this.ischeck =false;
        this.own = null;
        this.listnum = 0;
    }
    public int getListnum() {
        return listnum;
    }

    public void setListnum(int listnum) {
        this.listnum = listnum;
    }

    public String getDetialname() {
        return detialname;
    }

    public void setDetialname(String detialname) {
        this.detialname = detialname;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

}
