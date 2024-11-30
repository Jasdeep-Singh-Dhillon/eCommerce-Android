package com.jasdeep.finalproject;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jasdeep.finalproject.Item.Item;
import com.jasdeep.finalproject.Item.ItemAdapter;
import java.util.ArrayList;


public class Home extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();
    RecyclerView itemsView;
    ItemAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getItems();
        itemsView = findViewById(R.id.storeItems);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemsView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(items);
        itemsView.setAdapter(adapter);
    }

    private void getItems() {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        DatabaseReference ref = data.getReference().child("items");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Item item = snapshot.getValue(Item.class);
                Log.d("DATABASE", item.toString());
                Log.d("DATABASE", snapshot.getKey());
                adapter.addItem(item);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Item item = snapshot.getValue(Item.class);
//                Log.d("DATABASE", item.toString());
                items.add(item);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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