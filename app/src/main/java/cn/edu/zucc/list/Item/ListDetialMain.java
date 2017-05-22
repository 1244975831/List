package cn.edu.zucc.list.Item;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ListDetialMain {
    private String detialname;
    private boolean ischeck;
    private int listnum;

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
