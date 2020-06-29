package com.mrmi.resistorcolorcodecalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class SpinnerAdapter extends BaseAdapter
{
    ArrayList<Integer> colors;
    Context context;

    public SpinnerAdapter(Context context, int bandCount, int bandNumber)
    {
        this.context = context;
        colors = new ArrayList<Integer>();

        int retrieve[] = context.getResources().getIntArray(R.array.spinnerColors);
        int multiplierIndex = 3;
        if(bandCount>4)
            multiplierIndex=4;

        for(int i = 0; i < retrieve.length; ++i)
        {
            int re = retrieve[i];
            //Fill the digit bands (first 2 in 4 band resistors, first 3 in 5 and 6 band resistors)
            if(((bandCount==4 && bandNumber<=2) || (bandCount>4 && bandNumber<=3)) && i<10)
                colors.add(re);
            //Fill the multiplier band(third in 4 band resistors, fourth in 5 and 6 band resistors)
            if(bandNumber==multiplierIndex)
                colors.add(re);
            //Fill the tolerance band (right after multiplier band)
            else if(bandNumber==multiplierIndex+1)
            {
                if(i==1 || i==2 || i==5 || i==6 || i==7 || i==8 || i==10 || i==11)
                    colors.add(re);
            }
            //Fill PPM band (6th band, appears in 6 band resistors)
            else if(bandNumber==6 && (i==1 || i==2 || i==3 || i==4 || i==6 || i==7))
                colors.add(re);
        }
    }

    @Override
    public int getCount()
    {
        return colors.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        return colors.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    //Set each spinner's element content to the color it's supposed to be
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int pos, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv = view.findViewById(android.R.id.text1);
        txv.setBackgroundColor(colors.get(pos));
        return view;
    }

}
