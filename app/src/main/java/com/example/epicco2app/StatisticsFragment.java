package com.example.epicco2app;

import android.os.Bundle;
import android.util.Log;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class StatisticsFragment extends Fragment {
    BarChart barChart ,barChart2;
    LineChart lineChart;
    PieChart pieChart;
    ArrayList<BarEntry> barEntriesMonths;
    RadioGroup selectTime;
    IODatabase io;
    String userID;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_statistics, container,false);
        barChart = (BarChart) layout.findViewById(R.id.chart1);         // Month Chart
        lineChart =(LineChart) layout.findViewById(R.id.chart2);        // Weight chart
        pieChart = (PieChart) layout.findViewById(R.id.chart3);         // Food
        barChart2 = (BarChart) layout.findViewById(R.id.chart4);        // Week chart
        //selectTime = (RadioGroup) layout.findViewById(R.id.TimeGroup);    // Time
        io = IODatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        /*barEntriesMonths = new ArrayList<>();
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
        barEntriesMonths.add(new BarEntry(45f,11));*/

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

        barEntriesMonths = new ArrayList<>();

        io.getUserFoodData(userID, new IODatabase.FirebaseCallback() {
            @Override
            public void onSuccess(ArrayList<FoodLogObject> foodList) {
                Log.v("Async", "FoodData read successful");

                ArrayList<Integer> monthTotalList = new ArrayList<Integer>(Collections.nCopies(12, 0));
                Integer m;
                Integer j;
                for (int i=0; i<foodList.size(); i++) {
                    j = foodList.get(i).logTime.month;
                    m = monthTotalList.get(j) + foodList.get(i).total;
                    monthTotalList.set(j, m);
                }

                Float entry;
                for (int i=0; i<monthTotalList.size(); i++){
                    entry = (float) monthTotalList.get(i);
                    barEntriesMonths.add(new BarEntry(entry,i));
                    System.out.println(barEntriesMonths.size());
                }

                BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 kulutus kg");
                BarData theData = new BarData(monthList,barDataSet);

                barChart.setData(theData);
                barChart.notifyDataSetChanged();
                barChart.invalidate();

                ArrayList pieEntry = new ArrayList();
                pieEntry.add(new Entry(foodList.get(2).meat,0));
                pieEntry.add(new Entry(foodList.get(2).restaurant,1));
                pieEntry.add(new Entry(foodList.get(2).plant,2));
                pieEntry.add(new Entry(foodList.get(2).dairy,3));

                PieDataSet pieDataSet = new PieDataSet(pieEntry,"CO2 kulutus ruoka-aineittain");

                ArrayList foodType  = new ArrayList();
                foodType.add("Liha");
                foodType.add("Ravintola");
                foodType.add("Kasvikset");
                foodType.add("Maitotuotteet");
                PieData pieData = new PieData(foodType,pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });

        BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 kulutus kg");
        BarData theData = new BarData(monthList,barDataSet);
        //BarData theData = new BarData(monthList,barDataSet);
        barChart.setData(theData);
        //barChart.setDescription("");
        //barChart.setBackgroundColor();
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        ArrayList<Entry> weightEntries = new ArrayList<>();
        weightEntries.add(new Entry(56, 0));
        weightEntries.add(new Entry(57, 1));
        weightEntries.add(new Entry(54, 2));
        weightEntries.add(new Entry(59, 3));
        weightEntries.add(new Entry(63, 4));
        LineDataSet lineDataSet = new LineDataSet(weightEntries,"Paino");
        LineData lineData = new LineData(monthList, lineDataSet);
        lineChart.setData(lineData);

        /*ArrayList pieEntry = new ArrayList();
                pieEntry.add(new Entry(50,0));
                pieEntry.add(new Entry(150,1));
                pieEntry.add(new Entry(75,2));
                pieEntry.add(new Entry(20,3));

                PieDataSet pieDataSet = new PieDataSet(pieEntry,"CO2 kulutus ruoka-aineittain");

                ArrayList foodType  = new ArrayList();
                foodType.add("Liha");
                foodType.add("Ravintola");
                foodType.add("Kasvikset");
                foodType.add("Maitotuotteet");

                PieData pieData = new PieData(foodType,pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                //pieChart.notifyDataSetChanged();
                //pieChart.invalidate();*/
        return layout;
    }

}
