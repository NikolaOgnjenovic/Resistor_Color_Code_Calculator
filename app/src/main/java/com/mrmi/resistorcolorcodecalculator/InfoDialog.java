package com.mrmi.resistorcolorcodecalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class InfoDialog extends AppCompatDialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.InfoDialogTheme);
        builder.setTitle("How resistors work")
                .setMessage("Resistors are passive two-terminal electrical components that implement electrical resistance as circuit elements.\n\n" +
                        "There are three types of resistors: \n\n1) Four band resistors - the first two bands represent the resistance digits, " +
                        "the third the multiplier and the fourth band represents the tolerance.\n\n" +
                        "2) Five band resistors - the first three bands represent the resistance digits, the fourth band represents the multiplier and the fifth band represents the tolerance.\n\n" +
                        "3) Six band resistors - they are essentially five band resistors with a sixth band which represents the temperature coefficient\n\n" +
                        "Digit bands use color hold the digits of resistance; multipliers use different colors to represent by which power of 10 the digits will be multiplied" +
                        "  (1, 10, 100, up to 1 billion, with the gold and silver colors representing 1/10 and 1/100); tolerance represents the percentage of error in the resistor's resistance; " +
                        "the resistor's temperature coefficient tells how much its value changes as its temperature changes."
                )
                .setPositiveButton("Close", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        return builder.create();
    }
}
