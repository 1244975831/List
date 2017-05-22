package cn.edu.zucc.list.Item;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ListMain {
    private int Listnum;
    private String Listname;
    private String own;

    public ListMain (String listname){
        this.Listname = listname;
    }
    public ListMain(){

    }
    public int getListnum() {
        return Listnum;
    }

    public void setListnum(int listnum) {
        Listnum = listnum;
    }

    public String getListname() {
        return Listname;
    }

    public void setListname(String listname) {
        Listname = listname;
    }

    public String getOwn() {
        return own;
    }

    public void setOwn(String own) {
        this.own = own;
    }

}
