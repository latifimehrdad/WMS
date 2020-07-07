package com.example.wms.views.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentWalletBinding;
import com.example.wms.viewmodels.wallet.VM_FragmentWallet;
import com.example.wms.viewmodels.wallet.VM_Wallet;
import com.example.wms.views.fragments.FragmentPrimary;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Wallet extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_Wallet vm_wallet;
    String[] months = {"فرردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند"};

    @BindView(R.id.chart)
    BarChart chart;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_wallet = new VM_Wallet(getContext());
            FragmentWalletBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_wallet, container, false
            );
            binding.setVMWallet(vm_wallet);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    public Wallet() {//_____________________________________________________________________ Start FragmentWallet
    }//_____________________________________________________________________________________________ End FragmentWallet


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetChart();
    }//_____________________________________________________________________________________________ End onStart


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetChart() {//_____________________________________________________________________ Start SetChart

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        //ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(270);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return months[(int) value % months.length];
            }
        });
        //xAxis.setValueFormatter(xAxisFormatter);

        //ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        // chart.setDrawLegend(false);
        setData(5, 10);

    }//_____________________________________________________________________________________________ End SetChart



    private void setData(int count, float range) {

        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0,1));
        values.add(new BarEntry(1,2));
        values.add(new BarEntry(2,3));
        values.add(new BarEntry(3,4));
        values.add(new BarEntry(4,5));
        values.add(new BarEntry(5,6));
        values.add(new BarEntry(6,7));
        values.add(new BarEntry(7,8));
        values.add(new BarEntry(8,9));
        values.add(new BarEntry(9,10));
        values.add(new BarEntry(10,11));
        values.add(new BarEntry(11,12));

        BarDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {

            set1 = new BarDataSet(values, "The year 2019");

            set1.setDrawIcons(false);

            List<Integer> colors = new ArrayList<>();
            colors.add(getContext().getResources().getColor(R.color.mlHomeMaxScore));
            colors.add(getContext().getResources().getColor(R.color.mlHomeAveScore));

            set1.setColors(colors);

//            int startColor1 = ContextCompat.getColor(context, android.R.color.holo_orange_light);
//            int startColor2 = ContextCompat.getColor(context, android.R.color.holo_blue_light);
//            int startColor3 = ContextCompat.getColor(context, android.R.color.holo_orange_light);
//            int startColor4 = ContextCompat.getColor(context, android.R.color.holo_green_light);
//            int startColor5 = ContextCompat.getColor(context, android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(context, android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(context, android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(context, android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(context, android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(context, android.R.color.holo_orange_dark);
//
//            List<GradientColor> gradientColors = new ArrayList<>();
//            gradientColors.add(new GradientColor(startColor1, endColor1));
//            gradientColors.add(new GradientColor(startColor2, endColor2));
//            gradientColors.add(new GradientColor(startColor3, endColor3));
//            gradientColors.add(new GradientColor(startColor4, endColor4));
//            gradientColors.add(new GradientColor(startColor5, endColor5));
//
//            set1.setGradientColors(gradientColors);


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.5f);

            chart.setData(data);
        }
    }



}
