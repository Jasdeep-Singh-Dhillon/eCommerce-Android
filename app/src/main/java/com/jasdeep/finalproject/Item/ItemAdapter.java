package com.jasdeep.finalproject.Item;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasdeep.finalproject.R;

import java.util.ArrayList;

import coil3.Image;
import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import coil3.request.ImageRequest;
import coil3.target.ImageViewTarget;
import coil3.target.Target;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    ArrayList<Item> storeItems;

    public ItemAdapter(ArrayList<Item> items) {
        this.storeItems = items;
    }

    public void addItem(Item item) {
        storeItems.add(item);
        notifyItemChanged(storeItems.size()-1);
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //TODO: Use glide for Images
        ImageLoader loader = SingletonImageLoader.get(holder.itemImage.getContext());
        ImageRequest request = new ImageRequest.Builder(holder.itemImage.getContext())
                .data(storeItems.get(position).getImageUrl())
                .target(new ImageViewTarget(holder.itemImage))
                .build();
        loader.enqueue(request);
        holder.itemName.setText(storeItems.get(position).getName());
        holder.itemDesc.setText(storeItems.get(position).getDescription());
        holder.itemPrice.setText("$"+storeItems.get(position).getCost().toString());

        holder.itemBuyBtn.setOnClickListener(view -> {
            holder.itemName.setText("Clicked Buy");
        });

        holder.itemCartBtn.setOnClickListener(view -> {
            holder.itemName.setText("Clicked Add to Cart");
        });
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDesc;
        private TextView itemPrice;
        private Button itemBuyBtn;
        private Button itemCartBtn;
        public ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item, parent, false));
            itemImage = itemView.findViewById(R.id.itemIcon);
            itemName = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemBuyBtn = itemView.findViewById(R.id.buyBtn);
            itemCartBtn = itemView.findViewById(R.id.addCartBtn);
        }
    }
}
