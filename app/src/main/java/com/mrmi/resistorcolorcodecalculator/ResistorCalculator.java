package com.mrmi.resistorcolorcodecalculator;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ResistorCalculator extends AppCompatActivity
{
    Spinner[] bandSpinnerArray; //Array of spinners for all 6 bands
    ImageView[] bandImageArray; //Array of image views for all 6 bands
    TextView[] bandTextViewArray; //Array of text views for all 6 bands

    int bandCount; //Gotten from the main activity - how many bands the resistor the user is looking at has

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistor_calculator);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Lock activity into portrait mode

        //Initialise band spinner, image and text view arrays onCreate
        bandSpinnerArray = new Spinner[]{findViewById(R.id.firstBandSpinner), findViewById(R.id.secondBandSpinner), findViewById(R.id.thirdBandSpinner),
                findViewById(R.id.fourthBandSpinner), findViewById(R.id.fifthBandSpinner), findViewById(R.id.sixthBandSpinner)};
        bandImageArray = new ImageView[]{findViewById(R.id.firstBand),findViewById(R.id.secondBand), findViewById(R.id.thirdBand),
                findViewById(R.id.fourthBand), findViewById(R.id.fifthBand),findViewById(R.id.sixthBand)};
        bandTextViewArray = new TextView[]{findViewById(R.id.firstBandText), findViewById(R.id.secondBandText), findViewById(R.id.thirdBandText),
                findViewById(R.id.fourthBandText), findViewById(R.id.fifthBandText), findViewById(R.id.sixthBandText)};

        //Shown in 5 and 6 band resistors
        bandTextViewArray[2].setText("Third resistance digit (third band)");
        bandTextViewArray[3].setText("Multiplier (fourth band)");
        bandTextViewArray[4].setText("Tolerance (fifth band)");

        bandCount = getIntent().getIntExtra("BandCount", 4);
        //Disable sixth band text, spinner and image if activity is launched with intentExtra BandCount of less than 6
        if(bandCount<6)
        {
            bandTextViewArray[5].setVisibility(View.INVISIBLE);
            bandSpinnerArray[5].setVisibility(View.INVISIBLE);
            bandImageArray[5].setVisibility(View.INVISIBLE);
        }
        //Disable fifth band text, spinner and image if activity is launched with intentExtra BandCount of less than 5
        if(bandCount<5)
        {
            bandTextViewArray[4].setVisibility(View.INVISIBLE);
            bandSpinnerArray[4].setVisibility(View.INVISIBLE);
            bandImageArray[4].setVisibility(View.INVISIBLE);
            bandTextViewArray[2].setText("Multiplier (third band)");
            bandTextViewArray[3].setText("Tolerance (fourth band)");
        }

        //Set adapters for all 6 spinners
        short bandNumber = 1;
        for(Spinner spinner: bandSpinnerArray)
            spinner.setAdapter(new SpinnerAdapter(this, bandCount, bandNumber++));

        //Loop through all 6 bands set the band image's background color to its spinner's selected color every time an item is selected using a spinner
        for(int i = 0; i < 6; ++i)
        {
            final ImageView bandImage = bandImageArray[i];
            final Spinner spinner = bandSpinnerArray[i];

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    bandImage.setBackgroundColor((Integer) spinner.getSelectedItem());
                    calculateResistorProperties();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                }
            });
        }
    }

    private void calculateResistorProperties()
    {
        final TextView outputTextView = findViewById(R.id.outputTextView);
        String outputText="Resistor properties: \n";

        //Step 1: calculating the resistance digits:
        //On four band resistors the first two bands determine the resistance digits.
        double digits = bandSpinnerArray[0].getSelectedItemId()*10+bandSpinnerArray[1].getSelectedItemId();
        //Five and six band resistors use the third band for determining the third resistance digit
        if(bandCount>4)
            digits = digits*10 + bandSpinnerArray[2].getSelectedItemId();

        //Step 2: calculating the multiplier

        //Getting the multiplier band's info:
        int multiplierIndex;
        //4 band resistors use the third band for the multiplier
        if(bandCount==4)
            multiplierIndex = (int) bandSpinnerArray[2].getSelectedItemId();
            //5 and 6 band resistors use the fourth band for the multiplier
        else
            multiplierIndex = (int) bandSpinnerArray[3].getSelectedItemId();
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
            toleranceIndex = (int) bandSpinnerArray[3].getSelectedItemId();
            //5 and 6 band resistors use the fifth band for tolerance
        else
            toleranceIndex = (int) bandSpinnerArray[4].getSelectedItemId();
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
            int ppmIndex = (int) bandSpinnerArray[5].getSelectedItemId();
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

    //Formats resistance digits and returns them as a string with the appropriate suffix (k for kilo, M for mega, G for giga)
    private String formatDigits(double digits)
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
