package com.likabarken.cafeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    private static final String EXTRA_NAME = "userName";
    private static final String EXTRA_DRINK = "drink";
    private static final String EXTRA_DRINK_TYPE = "drinkType";
    private static final String EXTRA_ADDITIVES = "additives";

    private TextView textViewDate, textViewReceivedOrder, textViewDrink, textViewDrinkType, textViewAdditives;

    private String name, drink, drinkType, additives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupTextViews();
    }

    private void initViews() {
        textViewDate = findViewById(R.id.textViewDate);
        textViewReceivedOrder = findViewById(R.id.textViewReceivedOrder);
        textViewDrink = findViewById(R.id.textViewDrink);
        textViewDrinkType = findViewById(R.id.textViewDrinkType);
        textViewAdditives = findViewById(R.id.textViewAdditives);
    }

    private void setupTextViews() {
        name = getIntent().getStringExtra(EXTRA_NAME);
        textViewReceivedOrder.setText(getString(R.string.s_your_order_is_received, name));

        String current_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        textViewDate.setText(current_date);

        drink = getIntent().getStringExtra(EXTRA_DRINK);
        textViewDrink.setText(drink);

        drinkType = getIntent().getStringExtra(EXTRA_DRINK_TYPE);
        textViewDrinkType.setText(drinkType);

        additives = getIntent().getStringExtra(EXTRA_ADDITIVES);
        textViewAdditives.setText(additives);

    }
    public static Intent newIntent(
            Context context,
            String userName,
            String drink,
            String drinkType,
            String additives
    ) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(EXTRA_NAME, userName);
        intent.putExtra(EXTRA_DRINK, drink);
        intent.putExtra(EXTRA_DRINK_TYPE, drinkType);
        intent.putExtra(EXTRA_ADDITIVES, additives);
        return intent;
    }
}