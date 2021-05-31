package com.bd.foodsolution.orderdetails;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.foodsolution.MainActivity;
import com.bd.foodsolution.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class OrderDetails extends AppCompatActivity {
    private ImageView Product_Image;
    private Button otp_verify;
    private TextView Product_name,Product_price,Product_Quantity,name,Location,Total_price,Otp_Phone;
    private DatabaseReference database;
    private ConnectivityManager connectivityManager;

    boolean connected = false;
    private Toolbar toolbar;

    private StorageReference mStoragere;
    private DatabaseReference mDataBase;
    Uri foodImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        toolbar = findViewById(R.id.toolbar_orderDetails);
        setSupportActionBar(toolbar);
        setTitle("Order Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        otp_verify=findViewById(R.id.otp_verify);

        database = FirebaseDatabase.getInstance("https://food-solution-a5cbb-default-rtdb.firebaseio.com/").getReference("Order");

        name = findViewById(R.id.profileName);

        Product_Image=findViewById(R.id.Item_Image);

        Product_name=findViewById(R.id.Item_Name);
        Product_price=findViewById(R.id.Item_Price);
        Product_Quantity=findViewById(R.id.Item_Quantity);
        Location=findViewById(R.id.Address);
        Total_price=findViewById(R.id.total_price);
        Otp_Phone=findViewById(R.id.order_phone_number);

        final int pos=getIntent().getExtras().getInt("Image");
        Product_Image.setImageResource(pos);

        Product_name.setText("Product name: "+getIntent().getExtras().getString("Name"));

        name.setText("Name: "+getIntent().getExtras().getString("UserName"));
        Otp_Phone.setText("Number: "+getIntent().getExtras().getString("Otp_Phone"));


        String extra= getIntent().getStringExtra("Quantity");
        Product_Quantity.setText("Quantity: "+extra);
        Location.setText("Address: "+getIntent().getStringExtra("Location"));

        String value=getIntent().getExtras().getString("Price");

        int a=Integer.parseInt(extra);
        int b=Integer.parseInt(value);
        Product_price.setText("Price / Item: "+b);

        int sum=a*b;
        Total_price.setText("Total Price: "+String.valueOf(sum));


        otp_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else
                    connected = false;


                if(connected==true)
                {
                    String username = name.getText().toString();
                    String productname = Product_name.getText().toString();
                    String quantity = Product_Quantity.getText().toString();
                    String phone = Otp_Phone.getText().toString();
                    String address = Location.getText().toString();
                    String Total_Price=Total_price.getText().toString();


                    String key = database.push().getKey();
                    OrderGetterSetter orderItem = new OrderGetterSetter(username,productname,quantity,phone,address,Total_Price);
                    database.child(key).setValue(orderItem);
                    Toast.makeText(OrderDetails.this, "Order will be confirmed via Call", Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // for clrearing top activity from stack
                    startActivity(intent);
                    //finish();
                }
                else
                {

                    Snackbar.make(v,"Check Internet Connection !!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

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
