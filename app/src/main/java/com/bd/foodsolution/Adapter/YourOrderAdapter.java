package com.bd.foodsolution.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bd.foodsolution.orderdetails.OrderGetterSetter;
import com.bd.foodsolution.R;

import java.util.List;
import java.util.logging.Handler;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.OrderAdapter> {

    private Context context;
    private List<OrderGetterSetter>mArrayList;
    private Handler progressBarHandler;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;

    public YourOrderAdapter(Context context, List<OrderGetterSetter> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public OrderAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.yourorder_design,viewGroup,false);
        return new OrderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter orderAdapter, int i) {
        final OrderGetterSetter orderGetterSetter = mArrayList.get(i);
        //Picasso.get().load(orderGetterSetter.)
           //     .fit().centerInside().into(holder.papersImage);

        orderAdapter.tv1.setText(orderGetterSetter.getItemName());
        orderAdapter.tv2.setText(orderGetterSetter.getTotalPrice());
        orderAdapter.tv3.setText(orderGetterSetter.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class OrderAdapter extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv1,tv2,tv3;
        public OrderAdapter(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.item_name);
            tv2=itemView.findViewById(R.id.item_quantity);
            tv3=itemView.findViewById(R.id.item_price);
            //iv=itemView.findViewById(R.id.item_name);
        }
    }
}
