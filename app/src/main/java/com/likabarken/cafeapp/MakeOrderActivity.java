package com.likabarken.cafeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String EXTRA_USER_NAME = "userName";

    private TextView textViewGreetings, textViewAdditives;
    private RadioGroup radioGroupDrinks;
    private RadioButton radioButtonTea, radioButtonCoffee;
    private CheckBox checkBoxSugar, checkBoxMilk, checkBoxLemon;
    private Button buttonMakeAnOrder;
    private Spinner spinnerTea, spinnerCoffee;

    private String userName, drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupUserName();

        radioGroupDrinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id ==radioButtonTea.getId()) {
                    onUserChoseTea();
                } else if (id ==radioButtonCoffee.getId()) {
                    onUserChoseCoffee();
                }
            }
        });
        radioButtonTea.setChecked(true);

        buttonMakeAnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserMadeOrder();

            }
        });
    }

    private void initViews() {
        textViewGreetings = findViewById(R.id.textViewGreetings);
        textViewAdditives = findViewById(R.id.textViewAdditives);

        radioGroupDrinks = findViewById(R.id.radioGroupDrinks);
        radioButtonTea = findViewById(R.id.radioButtonTea);
        radioButtonCoffee = findViewById(R.id.radioButtonCoffee);

        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);

        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);

        buttonMakeAnOrder = findViewById(R.id.buttonMakeAnOrder);
    }

    private void onUserMadeOrder() {
        ArrayList<String> additives = new ArrayList<>();
        if (checkBoxSugar.isChecked()) {
            additives.add(checkBoxSugar.getText().toString());
        }
        if (checkBoxMilk.isChecked()) {
            additives.add(checkBoxMilk.getText().toString());
        }
        if (radioButtonTea.isChecked() && checkBoxLemon.isChecked()) {
            additives.add(checkBoxLemon.getText().toString());
        }

        String drinkType = "";
        if (radioButtonTea.isChecked()) {
            drinkType = spinnerTea.getSelectedItem().toString();
        } else if (radioButtonCoffee.isChecked()) {
            drinkType = spinnerCoffee.getSelectedItem().toString();
        }

        launchNextScreen(userName, drink, drinkType, additives.toString());
    }

    private void onUserChoseTea() {
        drink = getString(R.string.rb_tea);
        setupDrinkType(drink);
        checkBoxLemon.setVisibility(View.VISIBLE);
        spinnerTea.setVisibility(View.VISIBLE);
        spinnerCoffee.setVisibility(View.INVISIBLE);
    }

    private void onUserChoseCoffee() {
        drink = getString(R.string.rb_coffee);
        setupDrinkType(drink);
        checkBoxLemon.setVisibility(View.INVISIBLE);
        spinnerCoffee.setVisibility(View.VISIBLE);
        spinnerTea.setVisibility(View.INVISIBLE);
    }

    private void setupUserName() {
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String greetings = getString(R.string.tw_make_order, userName);
        textViewGreetings.setText(greetings);
    }

    private void setupDrinkType(String drink_type) {
        String result = getString(R.string.tw_additives, drink_type);
        textViewAdditives.setText(result);
    }
    private void launchNextScreen(String userName, String drink, String drinkType, String additives){
        Intent intent = OrderDetailActivity.newIntent(this,
                userName,
                drink,
                drinkType,
                additives);
        startActivity(intent);
    }

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }
}