package com.jasdeep.finalproject;

import static android.view.View.GONE;
import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.jasdeep.finalproject.Item.CartAdapter;
import com.jasdeep.finalproject.Item.Item;

import java.util.ArrayList;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();
    RecyclerView itemsView;
    CartAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    TextView netTotal;
    TextView tax;
    TextView total;
    Button checkout;
    Button back;

    TextView empty;

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
        empty = findViewById(R.id.empty);
        netTotal = findViewById(R.id.netTotalPriceTxt);
        tax = findViewById(R.id.taxPriceTxt);
        total = findViewById(R.id.totalPriceTxt);
        checkout = findViewById(R.id.checkoutBtn);
        back = findViewById(R.id.homeBtn);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemsView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(items);
        itemsView.setAdapter(adapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            itemsView.setLayoutManager(layoutManager);
        }

        checkout.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Checkout.class);
            startActivity(intent);
            finish();
        });

        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void updateCheckout() {
        int itemTotal = 0;
        for (Item item : items) {
            itemTotal += item.getCost() * item.getQuantity();

        }
        netTotal.setText(String.format(Locale.JAPAN, "¥ %d", itemTotal));
        tax.setText(String.format(Locale.JAPAN, "¥ %.0f", itemTotal * 0.1));
        total.setText(String.format(Locale.JAPAN, "¥ %.0f", itemTotal * 1.1));

        if(items.size() <= 0) {
            itemsView.setVisibility(GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            itemsView.setVisibility(View.VISIBLE);
            empty.setVisibility(GONE);
        }
    }

    private void getCartItems() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = data.child("cart").child(user.getUid());

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                DatabaseReference cartData = data.child("items").child(snapshot.getKey().toString());
                Integer quantity = parseInt(snapshot.getValue().toString());
                cartData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Item item = snapshot.getValue(Item.class);
                        assert item != null;
                        item.setQuantity(quantity);
                        adapter.addItem(item);
                        updateCheckout();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DatabaseReference cartData = data.child("items").child(snapshot.getKey().toString());
                Integer quantity = parseInt(snapshot.getValue().toString());
                cartData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Item item = snapshot.getValue(Item.class);
                        assert item != null;
                        item.setQuantity(quantity);
                        adapter.updateItem(item);
                        updateCheckout();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                updateCheckout();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}