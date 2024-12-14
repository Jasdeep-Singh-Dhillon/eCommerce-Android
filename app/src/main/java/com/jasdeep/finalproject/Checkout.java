package com.jasdeep.finalproject;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Checkout extends AppCompatActivity {
    EditText firstNameEdt,
            lastNameEdt,
            address1Edt,
            address2Edt,
            apartmentEdt,
            cityEdt,
            provinceEdt,
            postcodeEdt,
            cardHolderNameEdt,
            cardNumberEdt,
            cardExpirationEdt,
            cardCVVEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstNameEdt = findViewById(R.id.first_name);
        lastNameEdt = findViewById(R.id.last_name);
        address1Edt = findViewById(R.id.address_line_1);
        address2Edt = findViewById(R.id.address_line_2);
        apartmentEdt = findViewById(R.id.apartment);
        cityEdt = findViewById(R.id.city);
        provinceEdt = findViewById(R.id.province);
        postcodeEdt = findViewById(R.id.postcode);
        cardHolderNameEdt = findViewById(R.id.card_holder_name);
        cardNumberEdt = findViewById(R.id.credit_card_no);
        cardExpirationEdt = findViewById(R.id.card_expiration);
        cardCVVEdt = findViewById(R.id.card_cvv);


    }
}