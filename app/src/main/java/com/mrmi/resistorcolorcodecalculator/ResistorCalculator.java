package com.mrmi.resistorcolorcodecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class ResistorCalculator extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_band);

        Spinner firstBandSpinner = findViewById(R.id.firstBandSpinner), secondBandSpinner = findViewById(R.id.secondBandSpinner), thirdBandSpinner = findViewById(R.id.thirdBandSpinner),
                fourthBandSpinner = findViewById(R.id.fourthBandSpinner), fifthBandSpinner = findViewById(R.id.fifthBandSpinner), sixthBandSpinner = findViewById(R.id.sixthBandSpinner);
        TextView fifthBandText = findViewById(R.id.fifthBandText), sixthBandText = findViewById(R.id.sixthBandText);

        int bandCount = getIntent().getIntExtra("BandCount", 4);
        //Disable sixth band text and spinner if activity is launched with intentExtra BandCount of less than 6
        if(bandCount<6)
        {

            sixthBandText.setVisibility(View.INVISIBLE);
            sixthBandSpinner.setVisibility(View.INVISIBLE);
        }
        //Disable fifth band text and spinner if activity is launched with intentExtra BandCount of less than 5
        if(bandCount<5)
        {
            fifthBandText.setVisibility(View.INVISIBLE);
            fifthBandSpinner.setVisibility(View.INVISIBLE);
        }

        //Set adapters for all 6 spinners
        firstBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 1));
        secondBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 2));
        thirdBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 3));
        fourthBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 4));
        fifthBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 5));
        sixthBandSpinner.setAdapter(new SpinnerAdapter(this, bandCount, 6));
    }
}
