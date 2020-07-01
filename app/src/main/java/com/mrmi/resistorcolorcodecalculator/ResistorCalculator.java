package com.mrmi.resistorcolorcodecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResistorCalculator extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistor_calculator);

        final Spinner firstBandSpinner = findViewById(R.id.firstBandSpinner), secondBandSpinner = findViewById(R.id.secondBandSpinner), thirdBandSpinner = findViewById(R.id.thirdBandSpinner),
                fourthBandSpinner = findViewById(R.id.fourthBandSpinner), fifthBandSpinner = findViewById(R.id.fifthBandSpinner), sixthBandSpinner = findViewById(R.id.sixthBandSpinner);
        TextView fifthBandText = findViewById(R.id.fifthBandText), sixthBandText = findViewById(R.id.sixthBandText);

        final int bandCount = getIntent().getIntExtra("BandCount", 4);
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

        Button calculateButton = findViewById(R.id.calculateButton);
        final TextView outputTextView = findViewById(R.id.outputTextView);

        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String outputText="Resistor properties: \n";

                //Step 1: calculating the resistance digits:

                //On four band resistors the first two bands determine the resistance digits.
                double digits = firstBandSpinner.getSelectedItemId()*10+secondBandSpinner.getSelectedItemId();
                //Five and six band resistors use the third band for determining the third resistance digit
                if(bandCount>4)
                    digits = digits*10 + thirdBandSpinner.getSelectedItemId();

                //Step 2: calculating the multiplier

                //Getting the multiplier band's info:
                int multiplierIndex;
                //4 band resistors use the third band for the multiplier
                if(bandCount==4)
                    multiplierIndex = (int) thirdBandSpinner.getSelectedItemId();
                //5 and 6 band resistors use the fourth band for the multiplier
                else
                    multiplierIndex = (int) fourthBandSpinner.getSelectedItemId();
                //Multiply digits by 10 to the power of multiplierIndex (or divide them by 10 and 100 for multiplierIndexes of 10 and 11 respectively)
                switch(multiplierIndex)
                {
                    case 10: digits/=10; break;
                    case 11: digits/=100; break;
                    default: digits = (digits*Math.pow(10, multiplierIndex));
                }
                outputText += formatDigits(digits)+" Ω";

                //Step 3: calculating tolerance:
                int toleranceIndex;
                //4 band resistors use the fourth band for tolerance
                if(bandCount==4)
                    toleranceIndex = (int) fourthBandSpinner.getSelectedItemId();
                //5 and 6 band resistors use the fifth band for tolerance
                else
                    toleranceIndex = (int) fifthBandSpinner.getSelectedItemId();
                String toleranceText = " ± ";
                switch(toleranceIndex)
                {
                    case 0: toleranceText += "1"; break;
                    case 1: toleranceText += "2"; break;
                    case 2: toleranceText += "0.5"; break;
                    case 3: toleranceText += "0.25"; break;
                    case 4: toleranceText += "0.1"; break;
                    case 5: toleranceText += "0.05"; break;
                    case 6: toleranceText += "5"; break;
                    case 7: toleranceText += "10"; break;
                }
                toleranceText += "%";
                outputText += toleranceText;

                /*Step 4: calculating ppm in 6 band resistors
                6 band resistors use the 6th band to display ppm*/
                if(bandCount==6)
                {
                    int ppmIndex = (int) sixthBandSpinner.getSelectedItemId();
                    String ppmText = " ";
                    switch(ppmIndex)
                    {
                        case 0: ppmText += "100"; break;
                        case 1: ppmText += "50"; break;
                        case 2: ppmText += "15"; break;
                        case 3: ppmText += "25"; break;
                        case 4: ppmText += "10"; break;
                        case 5: ppmText += "5"; break;
                    }
                    ppmText += "ppm/°C";
                    outputText += ppmText;
                }

                outputTextView.setText(outputText);
            }
        });
    }

    //Formats resistance digits and returns them as a string with the appropriate suffix (k for kilo, M for mega, G for giga)
    public String formatDigits(double digits)
    {
        String selectedSuffix = "";

        //Loop through suffixes selecting them and dividing digits by 1000 if possible
        for (String suffix: new String[] {"k", "M", "G"})
        {
            if (digits < 1000)
                break;
            selectedSuffix = suffix;
            digits/=1000;
        }

        //Format the digits to show up to two decimal points if needed and add the appropriate suffix
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(digits) + selectedSuffix;
    }
}
