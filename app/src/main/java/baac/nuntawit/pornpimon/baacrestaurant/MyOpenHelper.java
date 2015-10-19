package baac.nuntawit.pornpimon.baacrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BAAC on 19/10/15.
 */
public class MyOpenHelper extends SQLiteOpenHelper
{
    // extends คือการให้ MyOpenHelper สืบทอดมาจาก SQLiteOpenHelper และเมื่อทำให้ Alt+Enter
    // Explicit ประกาศค่าตัวแปร มี 3 แบบ
    private static final String DATABASE_NAME = "BAAC.db"; //สร้างค่าตัวแปร DATABASE_NAME เป็น private แบบ static และ final ไม่สามารถแก้ไขได้
    private static final int DATABASE_VERSION = 1; //ต้องประกาศค่า version control ของ database เสมอ
    private static final String CREATE_USER_TABLE = "create table userTABLE (_id integer primary key, User text, Password text, Name text);"; //sqlite column ต้องเป็น _id integer เสมอ
    private static final String CREATE_FOOD_TABLE = "create table foodTABLE(_id integer primary key, Food text, Source text, Price text);";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //super คือคำสั่งที่ให้ทำงานอัตโนมัติ


    } //constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    // ใช้ตอนเปลี่ยน
    }
} //Main Class
