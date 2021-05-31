package com.bd.foodsolution.orderdetails;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bd.foodsolution.R;
import com.google.android.gms.ads.AdRequest;

public class OrderPage extends AppCompatActivity {
    private ImageView IV;
    private Button order;
    private TextView food_name;
    private EditText Food_quantity,User_Name,Order_Phone,Enter_Address;
    private String pric;
    private String codeSent;
    private Toolbar toolbar;
    int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        toolbar = findViewById(R.id.toolbar_orderpage);

        setSupportActionBar(toolbar);
        this.setTitle("Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User_Name = findViewById(R.id.userName);
        order=findViewById(R.id.Order);
        IV=findViewById(R.id.dish_view);
        Food_quantity=findViewById(R.id.food_quantity);
        food_name=findViewById(R.id.food_name);
        Enter_Address=findViewById(R.id.Address);
        Order_Phone= findViewById(R.id.Phone_Order_Page);

        final int pos=getIntent().getExtras().getInt("image");
        IV.setImageResource(pos);

        pric=getIntent().getExtras().getString("price");
        final String p=getIntent().getExtras().getString("text");
        food_name.setText("Selected : "+ p+"\n"+"Price : "+pric);



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Check_Phone=Order_Phone.getText().toString().trim();

                if(Check_Phone.isEmpty()) {
                    if(Order_Phone.length()<10) {
                        Order_Phone.setError("Phone number Required !!");
                        return;
                    }
                }
                else if(Check_Phone.length()<10)
                {
                    Order_Phone.setError("Check Phone Number");
                    return;
                }


                if(TextUtils.isEmpty(Enter_Address.getText().toString()))
                {
                    Enter_Address.setError("Please Provide Delivery Address");
                    return;
                }
                if(TextUtils.isEmpty(Food_quantity.getText().toString())) {
                    Food_quantity.setError("Food Quantity Required");
                    return;
                }

                if(TextUtils.isEmpty(User_Name.getText().toString())) {
                    User_Name.setError("Name Required");
                    return;
                }


                Intent i=new Intent(OrderPage.this,OrderDetails.class);
                i.putExtra("Image",pos);
                i.putExtra("Name",p);
                i.putExtra("Price",pric);

                i.putExtra("Otp_Phone",Order_Phone.getText().toString());
                i.putExtra("Quantity",Food_quantity.getText().toString());
                i.putExtra("Location",Enter_Address.getText().toString());
                i.putExtra("UserName",User_Name.getText().toString());
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
