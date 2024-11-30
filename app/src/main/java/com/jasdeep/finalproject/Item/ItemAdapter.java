package com.jasdeep.finalproject.Item;

import static java.lang.Integer.parseInt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jasdeep.finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import coil3.request.ImageRequest;
import coil3.target.ImageViewTarget;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    ArrayList<Item> storeItems;

    public ItemAdapter(ArrayList<Item> items) {
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

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ImageLoader loader = SingletonImageLoader.get(holder.itemImage.getContext());
        ImageRequest request = new ImageRequest.Builder(holder.itemImage.getContext())
                .data(storeItems.get(position).getImageUrl())
                .target(new ImageViewTarget(holder.itemImage))
                .build();
        loader.enqueue(request);
        holder.itemName.setText(storeItems.get(position).getName());
        holder.itemDesc.setText(storeItems.get(position).getDescription());
        holder.itemQuantity.setText(storeItems.get(position).getQuantity().toString());
        holder.itemPrice.setText("$" + storeItems.get(position).getCost().toString());

        holder.itemBuyBtn.setOnClickListener(view -> {
            Toast.makeText(holder.itemBuyBtn.getContext(), "Bought " + storeItems.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        holder.itemCartBtn.setOnClickListener(view -> {
            Item item = storeItems.get(position);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("cart")
                    .child(user.getUid())
                    .child(item.getId());
            assert user != null;

            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()) {
                        Object result = task.getResult().getValue();
                        if(result == null) {
                            ref.setValue(1);
                            return;
                        }
                       int quantity = parseInt(result.toString());
                       ref.setValue(quantity+1);
                    }
                }
            });
            Toast.makeText(holder.itemBuyBtn.getContext(), storeItems.get(position).getName() + " added to cart", Toast.LENGTH_SHORT).show();
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

        private TextView itemQuantity;
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
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
