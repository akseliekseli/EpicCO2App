package com.example.epicco2app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    BarChart barChart;
    ArrayList<String> monthList;
    ArrayList<BarEntry> barEntriesMonths;
    ArrayList<String> weekList; // Vai halutaanko päivittäinen enemmin
    ArrayList<BarEntry> barEntriesWeeks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        barChart = (BarChart) getView().findViewById(R.id.chart1);

        // Testattu lisämällä nyt vaan käsin arvoja listaa
        barEntriesMonths = new ArrayList<>();
        barEntriesMonths.add(new BarEntry(1, 80));
        barEntriesMonths.add(new BarEntry(2, 200));
        barEntriesMonths.add(new BarEntry(3, 40));
        barEntriesMonths.add(new BarEntry(4, 150));
        barEntriesMonths.add(new BarEntry(5, 45));
        barEntriesMonths.add(new BarEntry(6, 50));
        BarDataSet barDataSet = new BarDataSet(barEntriesMonths, "Co2 kulutus");
        // Näillä ei nyt josatin syystä toimi selvitän tätä vielä
        ArrayList<String> monthList = new ArrayList<>();
        monthList.add("Tammikuu");
        monthList.add("Helmikuu");
        monthList.add("Maaliskuu");
        monthList.add("Huhtikuu");
        monthList.add("Toukokuu");
        monthList.add("Kesäkuu");
        monthList.add("Heinäkuu");
        monthList.add("Elokuu");
        monthList.add("Syyskuu");
        monthList.add("Lokakuu");
        monthList.add("Marraskuu");
        monthList.add("Joulukuu");

        BarData theData = new BarData(barDataSet);

        // Testattu lisämällä nyt vaan käsin arvoja listaa
        /*barEntriesWeeks = new ArrayList<>();
        barEntriesWeeks.add(new BarEntry(1, 45));
        barEntriesWeeks.add(new BarEntry(2, 60));
        barEntriesWeeks.add(new BarEntry(3, 40));
        barEntriesWeeks.add(new BarEntry(4, 20));
        barEntriesWeeks.add(new BarEntry(5, 35));
        barEntriesWeeks.add(new BarEntry(6, 50));
        BarDataSet barDataSet2 = new BarDataSet(barEntriesWeeks, "Co2 kulutus");*/

        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
}
