package com.jasdeep.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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
    Button submitBtn;
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

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this, R.id.first_name, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.last_name, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.address_line_1, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.city, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.province, "[A-Za-z]{2}", R.string.provinceerror);
        validation.addValidation(this, R.id.postcode, "[A-Za-z][0-9][A-Za-z].?[0-9][A-Za-z][0-9]", R.string.postcodeerror);
        validation.addValidation(this, R.id.card_holder_name, ".{1,}", R.string.required);
        validation.addValidation(this, R.id.credit_card_no, "[0-9]{4}.?[0-9]{4}.?[0-9]{4}.?[0-9]{4}", R.string.cardnoerror);
        validation.addValidation(this, R.id.card_expiration, "[0-3]?[0-9]/2[4-9]", R.string.cardexpiryerror);
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

            startActivity(new Intent(getApplicationContext(), ThankYou.class));
            finish();

        });

    }

    private String getInput(EditText edtTxt) {
        return edtTxt.getEditableText().toString();
    }
}