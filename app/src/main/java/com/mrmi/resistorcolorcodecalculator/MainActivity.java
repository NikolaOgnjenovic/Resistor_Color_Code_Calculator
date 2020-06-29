package com.mrmi.resistorcolorcodecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Launch ResistorCalculator activity using the selected number of bands via buttons
        Button fourBandButton = findViewById(R.id.fourBandButton), fiveBandButton = findViewById(R.id.fiveBandButton), sixBandButton = findViewById(R.id.sixBandButton);
        fourBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent launchResistorPicker = new Intent(MainActivity.this, ResistorCalculator.class);
                launchResistorPicker.putExtra("BandCount", 4);
                startActivity(launchResistorPicker);
            }
        });

        fiveBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent launchResistorPicker = new Intent(MainActivity.this, ResistorCalculator.class);
                launchResistorPicker.putExtra("BandCount", 5);
                startActivity(launchResistorPicker);
            }
        });

        sixBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent launchResistorPicker = new Intent(MainActivity.this, ResistorCalculator.class);
                launchResistorPicker.putExtra("BandCount", 6);
                startActivity(launchResistorPicker);
            }
        });
    }
}
