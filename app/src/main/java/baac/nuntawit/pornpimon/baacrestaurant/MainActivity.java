package baac.nuntawit.pornpimon.baacrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget ถูกตัวแปรที่ userEditText กับ wid
        bindWidget();




    //Create & Connected Database สร้าง Method ย่อย
        createAndConnected(); //พิมพ์มี ()เป็น Method กด Alt+Enter ให้ระบบสร้าง Method ให้อัตโนมัติ

    //TesterAdd
    //  testerAdd();

     //Delete All SQLite
        DeleteAllSQLite();
        //Syn
        synJSONtoSQLite();






    } //Main Method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    public void clickLogin(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("")  || passwordString.equals("")) {

            //Have Space
            errorDialog("Have Space","Please Fill All Every blank");

        } else {
            //No Space
            checkUser();
        }
    }

    private void checkUser() {
        try {
            String[] strMyResult = objUserTABLE.searchUser(userString);
            if (passwordString.equals(strMyResult[2])) {
                Toast.makeText(MainActivity.this,"Welcome "+strMyResult[3],Toast.LENGTH_LONG).show();

                //Intent to OrderActivity จากหน้าหนึ่งไปสู่อีกหน้าหนึ่ง
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Name", strMyResult[3]);
                startActivity(objIntent);
                finish();



            } else {
                errorDialog("Password False","Please Try again Password False");
            }
        } catch (Exception e) {
            errorDialog("No This User","No "+ userString + " on my Database");
        }


    }

    private void errorDialog(String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }


    private void synJSONtoSQLite() {

        //0. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //permitAll เปิด policy ทุกตัว
        StrictMode.setThreadPolicy(myPolicy);


        int intTimes = 0; //เริ่ม Syn ตามจำนวนตาราง
        while (intTimes <=1) {
            InputStream objInputStream = null;
            String strJSON = null;
            String strUserURL = "http://swiftcodingthai.com/baac/php_get_data_master.php"; //ตำแหน่ง url ที่มี JSON
            String strFoodURL = "http://swiftcodingthai.com/baac/php_get_food.php";
            HttpPost objHttpPost; //protocal ที่ใช้สำหรับ Apache Protocal คำสั่งมี ขีดฆ่าคำสั่ง

            //1. Create InputStream
            try {
                // try ถ้า error ไม่ต้องหยุด code ให้ทำต่อไป
                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTimes) {
                    case 0:

                        objHttpPost = new HttpPost(strUserURL);
                        break;
                    case 1:

                        objHttpPost = new HttpPost(strFoodURL);
                        break;
                    default: //กรณี Switch ไม่เท่ากับ 0 หรือ 1
                        objHttpPost = new HttpPost(strUserURL);
                        break;
                }
                //Create InputStream ต้องมี 3 บรรทัดด้านล่าง
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("baac", "InputStream ==>" + e.toString());
            }

            //2. Create JSON String
            try {
                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream,"UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);

                } //while
                objInputStream.close();
                strJSON = objStringBuilder.toString();
            } catch (Exception e) {
                Log.d("baac", "styJSON ==>" + e.toString());
            }

            //3. Update SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject object = objJsonArray.getJSONObject(i);
                    switch (intTimes) {
                        case 0:
                            // For userTABLE
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            objUserTABLE.addNewUser(strUser, strPassword, strName);
                            break;
                        case 1:
                            // For foodTABLE
                            String strFood = object.getString("Food");
                            String strSource = object.getString("Source");
                            String strPrice = object.getString("Price");
                            objFoodTABLE.addNewFood(strFood, strSource, strPrice);
                            break;
                    } //switch
                } //for

            } catch (Exception e) {
                Log.d("baac", "Update ==>" + e.toString());
            }
            intTimes += 1;
        }

    } //Syn

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
