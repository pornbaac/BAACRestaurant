package baac.nuntawit.pornpimon.baacrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BAAC on 19/10/15.
 */
public class FoodTABLE {

    //Explicit

    private MyOpenHelper OBJMyOpenHelper;
    private SQLiteDatabase wrSqLiteDatabase,readSqLiteDatabase;
    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";



    public FoodTABLE(Context context) {
    OBJMyOpenHelper = new MyOpenHelper(context);
    wrSqLiteDatabase = OBJMyOpenHelper.getWritableDatabase();
    readSqLiteDatabase = OBJMyOpenHelper.getReadableDatabase();


    } //Construtor
    public long addNewFood(String strFood, String strSource, String strPrice) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FOOD,strFood);
        objContentValues.put(COLUMN_SOURCE,strSource);
        objContentValues.put(COLUMN_PRICE,strPrice);
        return wrSqLiteDatabase.insert(FOOD_TABLE,null,objContentValues);

    }


} //Main Class
