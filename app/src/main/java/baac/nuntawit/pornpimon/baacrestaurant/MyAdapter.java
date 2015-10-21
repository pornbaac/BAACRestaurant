package baac.nuntawit.pornpimon.baacrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by BAAC on 21/10/15.
 */
public class MyAdapter extends BaseAdapter{
    //Explicit
    private Context objContext;
    private String[] sourceStrings, foodStrings, priceStrings;

    public MyAdapter(Context objContext, String[] sourceStrings, String[] foodStrings, String[] priceStrings) {
        this.objContext = objContext;
        this.sourceStrings = sourceStrings;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;


    } //Constructor

    @Override
    public int getCount() {
        return foodStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //การเปิด Service ให้สามารถทำงานกับ Adepter
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView1 = objLayoutInflater.inflate(R.layout.food_listview,viewGroup,false);

        //For Show Food
        TextView foodTextView = (TextView) objView1.findViewById(R.id.txtShowFood);
        foodTextView.setText(foodStrings[i]);

        //For Show Price
        TextView priceTextView = (TextView) objView1.findViewById((R.id.txtShowPrice));
        priceTextView.setText(priceStrings[i]);

        //For Icon
        ImageView iconImageView = (ImageView) objView1.findViewById(R.id.imvIcon);
        Picasso.with(objContext).
                load(sourceStrings[i]).
                resize(120, 120).
                into(iconImageView);



        return objView1;
    }
} // Main Class
