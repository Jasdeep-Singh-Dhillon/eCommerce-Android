package com.jasdeep.finalproject.Item;

import static java.lang.Integer.parseInt;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jasdeep.finalproject.R;

import java.util.ArrayList;

import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import coil3.request.ImageRequest;
import coil3.target.ImageViewTarget;

public class ItemPagerAdapter extends RecyclerView.Adapter<ItemPagerAdapter.ItemViewHolder> {

    ArrayList<Item> storeItems;

    public ItemPagerAdapter(ArrayList<Item> items) {
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
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.itemPrice.setText("¥" + storeItems.get(position).getCost().toString());

        Item item = storeItems.get(holder.getAdapterPosition());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("cart")
                .child(user.getUid())
                .child(item.getId())
                .getRef().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object obj = task.getResult().getValue();
                        if (obj != null) {
                            holder.quantityEdt.setText(obj.toString());
                        }
                    }
                });

        holder.quantityEdt.addTextChangedListener(new TextWatcher() {
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

                String quantity = holder.quantityEdt.getEditableText().toString();
                if(quantity.isEmpty()) {
                    return;
                }
                if(parseInt(quantity) > 30) {
                    ref.setValue(Integer.parseInt("30"));
                    return;
                }
                ref.setValue(Integer.parseInt(quantity));
            }
        });

        holder.decreaseBtn.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.quantityEdt.getEditableText().toString());
            quantity--;
            if (quantity < 1) {
                return;
            }
            if (quantity > 30) {
                quantity = 30;
            }
            holder.quantityEdt.setText(String.valueOf(quantity));
        });

        holder.increaseBtn.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.quantityEdt.getEditableText().toString());
            quantity++;
            if (quantity > 30) {
                holder.quantityEdt.setText(String.valueOf(30));
                return;
            }
            holder.quantityEdt.setText(String.valueOf(quantity));
        });

        holder.itemBuyBtn.setOnClickListener(view -> {
            Toast.makeText(holder.itemBuyBtn.getContext(), "Bought " + storeItems.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        holder.itemCartBtn.setOnClickListener(view -> {
            assert user != null;
            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("cart")
                    .child(user.getUid())
                    .child(item.getId());

            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        Object result = task.getResult().getValue();
                        if (result == null) {
                            ref.setValue(1);
                            return;
                        }
                        int quantity = parseInt(holder.quantityEdt.getEditableText().toString());
                        if (quantity <= 0) {
                            return;
                        }
                        if (quantity > 30) {
                            quantity = 30;
                        }
                        ref.setValue(quantity);
                        holder.quantityEdt.setText(String.valueOf(quantity));
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
        private Button decreaseBtn;
        private EditText quantityEdt;
        private Button increaseBtn;
        private Button itemBuyBtn;
        private Button itemCartBtn;

        public ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_viewpager, parent, false));
            itemImage = itemView.findViewById(R.id.itemIcon);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemDesc = itemView.findViewById(R.id.itemDescription);
            itemBuyBtn = itemView.findViewById(R.id.buyBtn);
            itemCartBtn = itemView.findViewById(R.id.addCartBtn);
            decreaseBtn = itemView.findViewById(R.id.decreaseQuantity);
            increaseBtn = itemView.findViewById(R.id.increaseQuantity);
            quantityEdt = itemView.findViewById(R.id.itemQuantity);
        }
    }
}

