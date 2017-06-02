package cn.edu.zucc.list.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.Item.UserMain;


/**
 * Created by Administrator on 2016/12/11.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    private Context mContext;
    private int TEXT_CONTENT = 0;//纯文本
    private int LINK_CONTENT = 1;//连接内容
    private int IMAGE_CONTENT = 2;//图片内容

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        mContext=context;
    }

    public void initData(){
//        dbHelper=new DatabaseHelper(MainActivity.this,"Pokemon.db",null,1);
//        dbHelper.getWritableDatabase();
//        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        try {
            String User = InputUserData.User;
            JSONArray jsonArray=new JSONArray(User);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String id = jsonObject.getString("id");
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                //写入User表
//                values.put("id",id);
                values.put("username",username);
                values.put("password",password);
                values.put("name","路人甲");
                values.put("email","测试邮箱@163.com");
                db.insert("User",null,values);
                values.clear();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            String List = InputUserData.List;
            JSONArray jsonArray=new JSONArray(List);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String id = jsonObject.getString("id");
                String username = jsonObject.getString("listname");
                String password = jsonObject.getString("own");
                //写入User表
//                values.put("id",id);
                values.put("listname",username);
                values.put("own",password);

                db.insert("List",null,values);
                values.clear();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            String Detial = InputUserData.Detial;
            JSONArray jsonArray=new JSONArray(Detial);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                String detialname = jsonObject.getString("detialname");
                String own = jsonObject.getString("own");
                String finished =jsonObject.getString("finished");

                //写入User表
//                values.put("id",id);
                values.put("detialname",detialname);
                values.put("own",own);
                values.put("finished",finished);
                db.insert("Listdetial",null,values);
                values.clear();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void signData(String name , String pwd){
//        dbHelper=new DatabaseHelper(MainActivity.this,"Pokemon.db",null,1);
//        dbHelper.getWritableDatabase();
//        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        try {
            //写入User表
            values.put("username",name);
            values.put("password",pwd);
            values.put("name","路人甲");
            values.put("email","测试邮箱@163.com");
            db.insert("User",null,values);
            values.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void addList(String listname , String own){
        ContentValues values=new ContentValues();

        try {
            //写入List表
            values.put("listname",listname);
            values.put("own",own);
            db.insert("List",null,values);
            values.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void addListdetial(String listdetialname , int own){
        ContentValues values=new ContentValues();

        try {
            //写入Listdetial表
            values.put("detialname",listdetialname);
            values.put("own",own);
            values.put("finished",false);
            db.insert("Listdetial",null,values);
            values.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void Saveuser(String username , String password){
        ContentValues values=new ContentValues();

        try {
            //写入List表
            values.put("username",username);
            values.put("password",password);
            db.insert("Saveuser",null,values);
            values.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public UserMain selectSaveuser() {
        SQLiteDatabase dp=helper.getWritableDatabase();
        UserMain result = new UserMain();
        Cursor cursor = dp.query("Saveuser",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                UserMain datas = new UserMain();
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                result.setUsername(username);
                result.setPassword(password);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    public UserMain selectUsers(String name) {
        ArrayList<UserMain> data = new ArrayList<>();
        SQLiteDatabase dp=helper.getWritableDatabase();
        UserMain result = new UserMain();
        Cursor cursor = dp.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                UserMain datas = new UserMain();
//                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String names = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
//                datas.setId(id);
                datas.setUsername(username);
                datas.setPassword(password);
                datas.setName(names);
                datas.setEmail(email);

                data.add(datas);
            }while (cursor.moveToNext());
        }
        cursor.close();
        for(int i = 0;i < data.size() ; i++){
            if (data.get(i).getUsername().equals(name)){
                result = data.get(i);
                break;
            }
        }
        return result;
    }

    public   ArrayList<ListMain> selectList(String name) {
        ArrayList<ListMain> data = new ArrayList<>();
        SQLiteDatabase dp=helper.getWritableDatabase();
        ArrayList<ListMain> result = new ArrayList();
        Cursor cursor = dp.query("List",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                ListMain datas = new ListMain();
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String listname = cursor.getString(cursor.getColumnIndex("listname"));
                String own = cursor.getString(cursor.getColumnIndex("own"));
                datas.setListnum(id);
                datas.setListname(listname);
                datas.setOwn(own);
                data.add(datas);
            }while (cursor.moveToNext());
        }
        cursor.close();
        for(int i = 0;i < data.size() ; i++){
            if (data.get(i).getOwn().equals(name)){
                result.add(data.get(i));
            }
        }
        return result;
    }

    public  ArrayList<ListDetialMain> selectDetial(int num) {
        ArrayList<ListDetialMain> data = new ArrayList<>();
        SQLiteDatabase dp=helper.getWritableDatabase();
        ArrayList<ListDetialMain> result = new ArrayList<>();
        Cursor cursor = dp.query("Listdetial",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                ListDetialMain datas = new ListDetialMain();
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String detialname = cursor.getString(cursor.getColumnIndex("detialname"));
                String own = cursor.getString(cursor.getColumnIndex("own"));
                String finished = cursor.getString(cursor.getColumnIndex("finished"));
                String ps =cursor.getString(cursor.getColumnIndex("ps"));
                String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                datas.setListnum(id);
                datas.setDetialname(detialname);
                if (finished.equals("1")){
                    datas.setIscheck(true);
                }else {
                    datas.setIscheck(false);
                }
//                datas.setIscheck(detialname);
                datas.setOwn(own);
                datas.setPs(ps);
                datas.setDeadline(deadline);
                data.add(datas);
            }while (cursor.moveToNext());
        }
        cursor.close();
        String key=num+"";
        for(int i =0;i<data.size() ; i++){
            if (data.get(i).getOwn().equals(key)){
                result.add(data.get(i));
            }
        }
        return result;
    }

    public void deleteSaveuser() {
        SQLiteDatabase dp=helper.getWritableDatabase();
        dp.delete("Saveuser",null,null);
    }

    public void deleteList(int listnum) {
        SQLiteDatabase dp=helper.getWritableDatabase();
        dp.delete("List","_id = ?",new String[]{listnum+""});
    }

    public void deletedetial(int detialnum) {
        SQLiteDatabase dp=helper.getWritableDatabase();
        dp.delete("Listdetial","_id = ?",new String[]{detialnum+""});
    }

    public void updatedetial(int detialnum,Boolean type){
        SQLiteDatabase dp=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("finished",type);
        dp.update("Listdetial",values,"_id = ?",new String[]{detialnum+""});
    }

    public void updatedetialps(int detialnum,String ps){
        SQLiteDatabase dp=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ps",ps);
        dp.update("Listdetial",values,"_id = ?",new String[]{detialnum+""});
    }

    public void updatedetialtime(int detialnum,String time){
        SQLiteDatabase dp=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deadline",time);
        dp.update("Listdetial",values,"_id = ?",new String[]{detialnum+""});
    }

    public void updateusername(String username,String name){
        SQLiteDatabase dp=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        dp.update("User",values,"username = ?",new String[]{username});
    }

    public ArrayList<ListDetialMain> searchDetailName(String detailName,String username) {
        ArrayList<ListDetialMain> data = new ArrayList<>();
        SQLiteDatabase dp = helper.getWritableDatabase();

        String[] selectionArgs = new String[]{"%"+detailName+"%"};


        Cursor cursor = dp.rawQuery("select * from Listdetial,List where detialName like ? and List._id = Listdetial.own",selectionArgs);

       /* Cursor cursor = dp.query("Listdetial", null, "detialName = ? ", selectionArgs, null, null, null);*/

        if (cursor.moveToFirst()) {
            do {
                ListDetialMain datas = new ListDetialMain();
                datas.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                datas.setOwn(cursor.getString(cursor.getColumnIndex("own")));
                datas.setDetialname(cursor.getString(cursor.getColumnIndex("detialname")));
                String finished=cursor.getString(cursor.getColumnIndex("finished"));

                if (finished.equals("1")){
                    datas.setIscheck(true);
                }else {
                    datas.setIscheck(false);
                }
                data.add(datas);
            } while (cursor.moveToNext());
        }
        ArrayList<ListDetialMain> data2 = new ArrayList<>();
        for(int i = 0 ;i<data.size();i++){
            if(data.get(i).getOwn().equals(username)){
                data2.add(data.get(i));
            }
        }
        cursor.close();
        return data2;
    }

    String key = "";
    public void ReadData(){
        Cursor cursor=db.query("EvolveLine",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            key = key + "EType1:"+cursor.getString(cursor.getColumnIndex("etype1"))+"\n";
            key = key + "EType2:"+cursor.getString(cursor.getColumnIndex("etype2"))+"\n";
            key = key + "EType3:"+cursor.getString(cursor.getColumnIndex("etype3"))+"\n";
        }
        cursor.close();
    }




}