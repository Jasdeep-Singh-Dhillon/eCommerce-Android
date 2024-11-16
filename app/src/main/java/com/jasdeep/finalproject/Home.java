package com.jasdeep.finalproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jasdeep.finalproject.Item.Item;
import com.jasdeep.finalproject.Item.ItemAdapter;

import java.util.ArrayList;

import kotlinx.coroutines.channels.ReceiveChannel;

public class Home extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();
    RecyclerView itemsView;
    RecyclerView.Adapter adapter;
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
        Item item;
        item = new Item("UltraBall",
                300,
                "Ultra Ball Description",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png"
        );
        items.add(item);
        item = new Item("Great Ball",
                200,
                "Description of Great Ball",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/great-ball.png"
        );
        items.add(item);
        item = new Item("UltraBall",
                300,
                "Ultra Ball Description",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png"
        );
        items.add(item);
        item = new Item("Great Ball",
                200,
                "Description of Great Ball",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/great-ball.png"
        );
        items.add(item);
        item = new Item("UltraBall",
                300,
                "Ultra Ball Description",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png"
        );
        items.add(item);
        item = new Item("Great Ball",
                200,
                "Description of Great Ball",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/great-ball.png"
        );
        items.add(item);
        item = new Item("UltraBall",
                300,
                "Ultra Ball Description",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png"
        );
        items.add(item);
        item = new Item("Great Ball",
                200,
                "Description of Great Ball",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/great-ball.png"
        );
        items.add(item);
        item = new Item("UltraBall",
                300,
                "Ultra Ball Description",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png"
        );
        items.add(item);
        item = new Item("Great Ball",
                200,
                "Description of Great Ball",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/great-ball.png"
        );
        items.add(item);

    }
}