package com.example.epicco2app;

import android.icu.util.Calendar;
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
    BarChart barChart ,weekChart;
    LineChart lineChart;
    PieChart pieChart;
    ArrayList<BarEntry> barEntriesMonths, barEntriesWeeks;
    ArrayList<Entry> pieEntry, weightEntries;
    ArrayList<String> monthList;


    RadioGroup selectTime;
    IODatabase io;
    String userID;


    /*
    On create the basic charts are made using only 0 values because the data is requested with asynchronous call
    so it takes time to get the data. After the request is successful the charts are updated.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_statistics, container,false);
        barChart = (BarChart) layout.findViewById(R.id.chart1);         // Month Chart
        lineChart =(LineChart) layout.findViewById(R.id.chart2);        // Weight chart
        pieChart = (PieChart) layout.findViewById(R.id.chart3);         // Food
        weekChart = (BarChart) layout.findViewById(R.id.chart4);        // Week chart
        //selectTime = (RadioGroup) layout.findViewById(R.id.TimeGroup);    // Time
        io = ((HomeActivity)getActivity()).io;
        userID = ((HomeActivity)getActivity()).userID;
        //selectTime = (RadioGroup) layout.findViewById(R.id.TimeGroup);
        //selectTime = (RadioGroup) layout.findViewById(R.id.TimeGroup);

        barEntriesMonths = new ArrayList<>();
        weightEntries = new ArrayList<>();
        pieEntry = new ArrayList();
        for (int i=0; i<12; i++){
            barEntriesMonths.add(new BarEntry(0f,i));

            weightEntries.add(new Entry(0,i));
        }
        for (int i=0; i<4; i++){
            pieEntry.add(new Entry(0,i));
        }


        monthList = new ArrayList<>();
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
        barEntriesWeeks = new ArrayList<>();

        BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 tuotto kg");
        BarData theData = new BarData(monthList,barDataSet);
        //BarData theData = new BarData(monthList,barDataSet);
        barChart.setData(theData);
        //barChart.setDescription("");
        //barChart.setBackgroundColor();
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        weekChart.setData(theData);
        weekChart.setTouchEnabled(true);
        weekChart.setDragEnabled(true);
        weekChart.setScaleEnabled(true);



        LineDataSet lineDataSet = new LineDataSet(weightEntries,"Paino");
        LineData lineData = new LineData(monthList, lineDataSet);
        lineChart.setData(lineData);

        PieDataSet pieDataSet = new PieDataSet(pieEntry,"CO2 tuotto ruoka-aineittain");

        ArrayList foodType  = new ArrayList();
        foodType.add("Liha");
        foodType.add("Ravintola");
        foodType.add("Kasvikset");
        foodType.add("Maitotuotteet");

        PieData pieData = new PieData(foodType,pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        /*
        This call is used to update food charts with user data
         */
        updateFoodCharts();
        updateWeightChart();



        return layout;
    }

    public void updateFoodCharts(){
        io.getUserFoodData(userID, new IODatabase.FirebaseCallback() {
            @Override
            public void onSuccess(ArrayList<FoodLogObject> foodList) {
                Log.v("Async", "FoodData read successful");
                ArrayList<Integer> differentTypesList = new ArrayList<Integer>(Collections.nCopies(4,0));
                ArrayList<Integer> monthTotalList = new ArrayList<Integer>(Collections.nCopies(12, 0));
                ArrayList<String> weekDate = new ArrayList<>();
                Integer m;
                Integer j;
                /*
                Making the data entries for the charts
                 */
                for (int i=0; i<foodList.size(); i++) {
                    j = foodList.get(i).logTime.month;
                    m = monthTotalList.get(j) + foodList.get(i).total;
                    monthTotalList.set(j, m);

                    differentTypesList.set(0, differentTypesList.get(0) + foodList.get(i).meat);
                    differentTypesList.set(1, differentTypesList.get(1) + foodList.get(i).restaurant);
                    differentTypesList.set(2, differentTypesList.get(2) + foodList.get(i).plant);
                    differentTypesList.set(3, differentTypesList.get(3) + foodList.get(i).dairy);

                    barEntriesWeeks.add(new BarEntry(foodList.get(i).total, i));
                    weekDate.add(foodList.get(i).logTime.day+"."+foodList.get(i).logTime.month+"."+foodList.get(i).logTime.year);

                }

                Float entry;
                for (int i=0; i<monthTotalList.size(); i++){
                    entry = (float) monthTotalList.get(i);
                    barEntriesMonths.add(new BarEntry(entry,i));

                }

                /*
                Updating the data
                 */
                BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 tuotto kg");
                BarData theData = new BarData(monthList,barDataSet);

                barChart.setData(theData);
                barChart.notifyDataSetChanged();
                barChart.invalidate();

                BarDataSet weekDataSet = new BarDataSet(barEntriesWeeks, "Co2 tuotto kg");
                BarData weekData = new BarData(weekDate,weekDataSet);

                weekChart.setData(weekData);
                weekChart.notifyDataSetChanged();
                weekChart.invalidate();

                ArrayList pieEntry = new ArrayList();
                pieEntry.add(new Entry(differentTypesList.get(0),0));
                pieEntry.add(new Entry(differentTypesList.get(1),1));
                pieEntry.add(new Entry(differentTypesList.get(2),2));
                pieEntry.add(new Entry(differentTypesList.get(3),3));

                PieDataSet pieDataSet = new PieDataSet(pieEntry,"CO2 tuoton kokonaismäärä ruoka-aineittain");

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
    }
    /*
    This requests the data from Firebase and updates line chart
     */
    public void updateWeightChart(){
        io.getUserWeight(userID, new IODatabase.WeightCallback() {
            @Override
            public void onSuccess(ArrayList<WeightLogObject> weightLogObjects) {

                ArrayList<Entry> weightEntries = new ArrayList<>();
                ArrayList<String> weightDate = new ArrayList<>();
                for (int i=0; i<weightLogObjects.size(); i++){
                    weightEntries.add(new Entry(weightLogObjects.get(i).weight, i));
                    weightDate.add(weightLogObjects.get(i).logTime.day+"."+weightLogObjects.get(i).logTime.month+"."+weightLogObjects.get(i).logTime.year);

                }

                LineDataSet weightDataSet = new LineDataSet(weightEntries,"Paino");
                LineData lineData = new LineData(weightDate, weightDataSet);
                lineChart.setData(lineData);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

            }
        });
    }

}
