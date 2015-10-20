package baac.nuntawit.pornpimon.baacrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Create & Connected Database สร้าง Method ย่อย
        createAndConnected(); //พิมพ์มี ()เป็น Method กด Alt+Enter ให้ระบบสร้าง Method ให้อัตโนมัติ

    //TesterAdd
    //  testerAdd();

     //Delete All SQLite
        DeleteAllSQLite();





    } //Main Method

    private void DeleteAllSQLite() {

        SQLiteDatabase objSqlDatabase = openOrCreateDatabase("BAAC.db",MODE_PRIVATE,null); //MODE_PRIVATE ลบข้อมูลในตารางแต่ยังคงตารางไว้
        objSqlDatabase.delete("userTABLE", null, null);
        objSqlDatabase.delete("foodTABLE", null, null);

    }

    private void testerAdd() {
        objUserTABLE.addNewUser("testUser", "testPassword", "ทดสอบชื่อภาษาไทย");
        objFoodTABLE.addNewFood("ชื่ออาหาร","testSource","123");
    }

    private void createAndConnected() {
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);
    }

} //Main Class
