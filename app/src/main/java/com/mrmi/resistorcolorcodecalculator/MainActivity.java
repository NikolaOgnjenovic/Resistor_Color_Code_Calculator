package com.mrmi.resistorcolorcodecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Lock activity into portrait mode

        //Launch ResistorCalculator activity using the selected number of bands via buttons
        Button fourBandButton = findViewById(R.id.fourBandButton), fiveBandButton = findViewById(R.id.fiveBandButton), sixBandButton = findViewById(R.id.sixBandButton);
        fourBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchCalculator(4);
            }
        });

        fiveBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchCalculator(5);
            }
        });

        sixBandButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchCalculator(6);
            }
        });
    }

    private void launchCalculator(int bandCount)
    {
        Intent launchResistorPicker = new Intent(MainActivity.this, ResistorCalculator.class);
        launchResistorPicker.putExtra("BandCount", bandCount);
        startActivity(launchResistorPicker);
    }
}
