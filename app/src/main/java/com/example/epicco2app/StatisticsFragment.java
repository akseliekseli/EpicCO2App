package com.example.epicco2app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    BarChart barChart;
    LineChart lineChart;
    PieChart pieChart;
    ArrayList<BarEntry> barEntriesMonths;

    RadioGroup selectTime;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_statistics, container,false);
        barChart = (BarChart) layout.findViewById(R.id.chart1);
        lineChart =(LineChart) layout.findViewById(R.id.chart2);
        pieChart = (PieChart) layout.findViewById(R.id.chart3);
        //selectTime = (RadioGroup) layout.findViewById(R.id.TimeGroup);

        barEntriesMonths = new ArrayList<>();
        barEntriesMonths.add(new BarEntry(80f,0));
        barEntriesMonths.add(new BarEntry(200f,1));
        barEntriesMonths.add(new BarEntry(150f,2));
        barEntriesMonths.add(new BarEntry(45f,3));
        barEntriesMonths.add(new BarEntry(50f,4));
        barEntriesMonths.add(new BarEntry(80f,5));
        barEntriesMonths.add(new BarEntry(200f,6));
        barEntriesMonths.add(new BarEntry(150f,7));
        barEntriesMonths.add(new BarEntry(45f,8));
        barEntriesMonths.add(new BarEntry(50f,9));
        barEntriesMonths.add(new BarEntry(150f,10));
        barEntriesMonths.add(new BarEntry(45f,11));

        ArrayList<String> monthList = new ArrayList<>();
        monthList.add("Tam");
        monthList.add("Hel");
        monthList.add("Maa");
        monthList.add("Huh");
        monthList.add("Tou");
        monthList.add("Kes");
        monthList.add("Hei");
        monthList.add("Elo");
        monthList.add("Syy");
        monthList.add("Lok");
        monthList.add("Mar");
        monthList.add("Jou");


        BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 kulutus");
        BarData theData = new BarData(monthList,barDataSet);
        //BarData theData = new BarData(monthList,barDataSet);
        barChart.setData(theData);
        //barChart.setDescription("");
        //barChart.setBackgroundColor();
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        ArrayList<Entry> barEntries2 = new ArrayList<>();
        barEntries2.add(new Entry(56, 1));
        barEntries2.add(new Entry(57, 2));
        barEntries2.add(new Entry(54, 3));
        barEntries2.add(new Entry(59, 4));
        barEntries2.add(new Entry(63, 5));
        barEntries2.add(new Entry(60,6 ));
        LineDataSet lineDataSet = new LineDataSet(barEntries2,"Paino");
        LineData lineData = new LineData(monthList, lineDataSet);
        lineChart.setData(lineData);

        ArrayList pieEntry = new ArrayList();
        pieEntry.add(new Entry(200,0));
        pieEntry.add(new Entry(100,0));
        pieEntry.add(new Entry(50,0));
        pieEntry.add(new Entry(20,0));
        pieEntry.add(new Entry(200,0));

        PieDataSet pieDataSet = new PieDataSet(pieEntry,"CO2 kulutus ruoka-aineittain");

        ArrayList foodType  = new ArrayList();
        foodType.add("Nauta ja lammas");
        foodType.add("Kana");
        foodType.add("Kasvikset");
        foodType.add("Kananmuna");
        foodType.add("Maitotuotteet");

        PieData pieData = new PieData(foodType,pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return layout;
    }

}
