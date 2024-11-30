package com.jasdeep.finalproject;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jasdeep.finalproject.Item.Item;
import com.jasdeep.finalproject.Item.ItemAdapter;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();
    RecyclerView itemsView;
    ItemAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getCartItems();

        itemsView = findViewById(R.id.cartItems);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemsView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(items);
        itemsView.setAdapter(adapter);
    }

    private void getCartItems() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference data = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = data.child("cart").child(user.getUid());
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                DatabaseReference cartData = data.child("items").child(snapshot.getKey().toString());
                Integer quantity = parseInt(snapshot.getValue().toString());
                cartData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Item item = snapshot.getValue(Item.class);
                        item.setQuantity(quantity);
                        adapter.addItem(item);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Item item = snapshot.getValue(Item.class);
                adapter.removeItem(item);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addChildEventListener(childEventListener);
    }
}