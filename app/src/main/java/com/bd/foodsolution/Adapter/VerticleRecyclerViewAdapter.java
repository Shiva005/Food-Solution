package com.bd.foodsolution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.foodsolution.orderdetails.OrderPage;
import com.bd.foodsolution.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerticleRecyclerViewAdapter extends RecyclerView.Adapter<VerticleRecyclerViewAdapter.ViewHolder>{

    private List FoodName;
    private List FoodImage;
    private List FoodPrice;

    private Context context;

    public VerticleRecyclerViewAdapter(Context context, List foodName, List foodImage, List foodPrice) {
        this.FoodName = foodName;
        this.FoodImage = foodImage;
        this.FoodPrice=foodPrice;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_food_items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText((CharSequence) FoodName.get(position));
        holder.image.setImageResource((Integer) FoodImage.get(position));
        holder.price.setText((CharSequence)FoodPrice.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, ""+FoodName.get(position), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, OrderPage.class);

                intent.putExtra("image", (Integer) FoodImage.get(position));
                intent.putExtra("text", FoodName.get(position).toString());
                intent.putExtra("price", FoodPrice.get(position).toString());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return FoodImage.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CircleImageView image;
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.circle_image_id);
            name=itemView.findViewById(R.id.food_title_id);
            price=itemView.findViewById(R.id.food_Price_id);
        }
    }
}
