package com.mrmi.resistorcolorcodecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

public class FourBand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_band);

        Spinner firstBandSpinner = findViewById(R.id.firstBandSpinner), secondBandSpinner = findViewById(R.id.secondBandSpinner), thirdBandSpinner = findViewById(R.id.thirdBandSpinner),
                fourthBandSpinner = findViewById(R.id.fourthBandSpinner);
        firstBandSpinner.setAdapter(new SpinnerAdapter(this));
        secondBandSpinner.setAdapter(new SpinnerAdapter(this));
        thirdBandSpinner.setAdapter(new SpinnerAdapter(this));
        fourthBandSpinner.setAdapter(new SpinnerAdapter(this));
    }
}
