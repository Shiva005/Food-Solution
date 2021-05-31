package com.bd.foodsolution.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.foodsolution.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>{

    private List names;
    private List imageUrl;
    private Context context;

    public HorizontalRecyclerViewAdapter(Context context, List names, List imageUrl) {

        this.context = context;
        this.names = names;
        this.imageUrl = imageUrl;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalview, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.name.setText((CharSequence) names.get(position));
        holder.image.setImageResource((Integer) imageUrl.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Under Maintenance"+names.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imagecircle);
            name=itemView.findViewById(R.id.textviewid);
        }
    }
}
