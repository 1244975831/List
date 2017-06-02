package cn.edu.zucc.list.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/11.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbdemo.db";
    private static final int DATABASE_VERSION = 5;
    private Context mContext;
    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户列表 id 用户名 密码
        db.execSQL("CREATE TABLE IF NOT EXISTS User" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT,name TEXT , email TEXT)");
        //清单列表
        db.execSQL("CREATE TABLE IF NOT EXISTS List" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, listname TEXT, own TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Listdetial" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, detialname TEXT, own TEXT , ps Text ,finished Text ,deadline TEXT )");

        db.execSQL("CREATE TABLE IF NOT EXISTS Saveuser" +
                "(username TEXT, password TEXT )");


        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();

    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}