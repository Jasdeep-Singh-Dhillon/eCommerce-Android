package com.jasdeep.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

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
    Button submitBtn, backBtn;
    AwesomeValidation validation;

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
        submitBtn = findViewById(R.id.submitBtn);
        backBtn = findViewById(R.id.backBtn);

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this, R.id.first_name, "[A-Za-z]{1,}", R.string.required);
        validation.addValidation(this, R.id.last_name, "[A-Za-z]x{1,}", R.string.required);
        validation.addValidation(this, R.id.address_line_1, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.city, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.province, "[A-Za-z]{2}", R.string.provinceerror);
        validation.addValidation(this, R.id.postcode, "[A-Za-z][0-9][A-Za-z].?[0-9][A-Za-z][0-9]", R.string.postcodeerror);
        validation.addValidation(this, R.id.card_holder_name, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.credit_card_no, "[0-9]{4}.?[0-9]{4}.?[0-9]{4}.?[0-9]{4}", R.string.cardnoerror);
        validation.addValidation(this, R.id.card_expiration, "^(0[1-9]|1[0-2]|[1-9])\\/2[4-9]", R.string.cardexpiryerror);
        validation.addValidation(this, R.id.card_cvv, "[0-9]{3}", R.string.cvverror);

        submitBtn.setOnClickListener(view -> {
            String firstName,
                    lastName,
                    address1,
                    address2,
                    apartment,
                    city,
                    province,
                    postcode,
                    cardHolderName,
                    cardNumber,
                    cardExpiration,
                    cardCvv;
            firstName = getInput(firstNameEdt);
            lastName = getInput(lastNameEdt);
            address1 = getInput(address1Edt);
            address2 = getInput(address2Edt);
            apartment = getInput(apartmentEdt);
            city = getInput(cityEdt);
            province = getInput(provinceEdt);
            postcode = getInput(postcodeEdt);
            cardHolderName = getInput(cardHolderNameEdt);
            cardNumber = getInput(cardNumberEdt);
            cardExpiration = getInput(cardExpirationEdt);
            cardCvv = getInput(cardCVVEdt);

            if(!validation.validate()) return;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid()).getRef();
            ref.removeValue();

            startActivity(new Intent(getApplicationContext(), ThankYou.class));
            finish();
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private String getInput(EditText edtTxt) {
        return edtTxt.getEditableText().toString();
    }
}