package com.jasdeep.finalproject.Item;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jasdeep.finalproject.R;

import java.util.ArrayList;

import coil3.Image;
import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import coil3.request.ImageRequest;
import coil3.target.ImageViewTarget;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {

    ArrayList<Item> storeItems;

    public CartAdapter(ArrayList<Item> items) {
        this.storeItems = items;
    }

    public void addItem(Item item) {
        storeItems.add(item);
        notifyItemChanged(storeItems.size() - 1);
    }

    public void updateItem(Item item) {
        for (int i = 0; i < storeItems.size(); i++) {
            if (storeItems.get(i).getId().equals(item.getId())) {
                storeItems.set(i, item);
                notifyItemChanged(i);
            }
        }
    }

    public void removeItem(Item item) {
        for (int i = 0; i < storeItems.size(); i++) {
            if (storeItems.get(i).getId().equals(item.getId())) {
                storeItems.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    public void removeItem(int position) {
        storeItems.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public CartAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ImageLoader loader = SingletonImageLoader.get(holder.itemImage.getContext());
        ImageRequest request = new ImageRequest.Builder(holder.itemImage.getContext())
                .data(storeItems.get(holder.getAdapterPosition()).getImageUrl())
                .target(new ImageViewTarget(holder.itemImage))
                .build();
        loader.enqueue(request);
        holder.itemName.setText(storeItems.get(holder.getAdapterPosition()).getName());
        holder.itemDesc.setText(storeItems.get(holder.getAdapterPosition()).getDescription());
        holder.itemQuantity.setText(storeItems.get(holder.getAdapterPosition()).getQuantity().toString());
        holder.itemPrice.setText("¥" + storeItems.get(holder.getAdapterPosition()).getCost().toString());

        holder.decreaseQuantity.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.itemQuantity.getEditableText().toString());
            quantity--;
            if (quantity < 1) {
                quantity = 1;
            }
            if (quantity > 30) {
                quantity = 30;
            }
            holder.itemQuantity.setText(String.valueOf(quantity));
        });

        holder.itemQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Item item = storeItems.get(holder.getAdapterPosition());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                DatabaseReference ref = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("cart")
                        .child(user.getUid())
                        .child(item.getId());

                String quantity = holder.itemQuantity.getEditableText().toString();
                if(quantity.isEmpty()) {
                    quantity = "1";
                }
                if(Integer.parseInt(quantity) > 30) {
                    quantity = "30";
                }
                ref.setValue(Integer.parseInt(quantity));
            }
        });

        holder.increaseQuantity.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.itemQuantity.getEditableText().toString());
            quantity++;
            if (quantity > 30) {
                quantity = 30;
            }
            holder.itemQuantity.setText(String.valueOf(quantity));
        });

        holder.updateCartBtn.setOnClickListener(view -> {
            Item item = storeItems.get(holder.getAdapterPosition());
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("cart")
                    .child(user.getUid())
                    .child(item.getId());

            String quantity = holder.itemQuantity.getEditableText().toString();
            if(Integer.parseInt(quantity) > 30) {
                quantity = "30";
                holder.itemQuantity.setText(quantity);
            }
            ref.setValue(Integer.parseInt(quantity));
        });


        holder.removeCartBtn.setOnClickListener(view -> {
            Item item = storeItems.get(holder.getAdapterPosition());
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("cart")
                    .child(user.getUid())
                    .child(item.getId())
                    .getRef();
            ref.removeValue();
            removeItem(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDesc;
        private TextView itemPrice;
        private EditText itemQuantity;
        private Button decreaseQuantity;
        private Button increaseQuantity;
        private Button updateCartBtn;
        private ImageView removeCartBtn;

        public ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cart_item, parent, false));
            cardView = itemView.findViewById(R.id.main);
            itemImage = itemView.findViewById(R.id.itemIcon);
            itemName = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            updateCartBtn = itemView.findViewById(R.id.updateBtn);
            removeCartBtn = itemView.findViewById(R.id.removeCartBtn);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
        }
    }
}
