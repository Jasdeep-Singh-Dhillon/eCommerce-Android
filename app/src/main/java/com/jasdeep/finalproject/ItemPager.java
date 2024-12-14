package com.jasdeep.finalproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.jasdeep.finalproject.Item.Item;
import com.jasdeep.finalproject.Item.ItemPagerAdapter;

import java.util.ArrayList;

public class ItemPager extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ItemPagerAdapter adapter;

    ArrayList<Item> items = Home.items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.item_view_pager);
        adapter = new ItemPagerAdapter(items);
        viewPager.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt(Home.POSITION, 0);
            viewPager.setCurrentItem(position);
        }


    }
}