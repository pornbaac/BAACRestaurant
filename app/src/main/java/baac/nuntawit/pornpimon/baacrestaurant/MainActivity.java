package baac.nuntawit.pornpimon.baacrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Create & Connected Database สร้าง Method ย่อย
        createAndConnected(); //พิมพ์มี ()เป็น Method กด Alt+Enter ให้ระบบสร้าง Method ให้อัตโนมัติ




    } //Main Method

    private void createAndConnected() {
        objUserTABLE = new UserTABLE(this);

    }

} //Main Class
