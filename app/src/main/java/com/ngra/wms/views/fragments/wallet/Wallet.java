package com.ngra.wms.views.fragments.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentWalletBinding;
import com.ngra.wms.models.MD_ChartReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.wallet.VM_Wallet;
import com.ngra.wms.views.adaptors.lottery.AP_WalletScore;
import com.ngra.wms.views.adaptors.lottery.AP_WalletScoreItem;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.ngra.wms.views.fragments.Login;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class Wallet extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    List<String> months;
    ArrayList<BarEntry> values;
    private VM_Wallet vm_wallet;

    @BindView(R.id.chart)
    BarChart chart;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewItem)
    RecyclerView RecyclerViewItem;

    @BindView(R.id.TextViewTotalPrice)
    TextView TextViewTotalPrice;

    @BindView(R.id.TextViewTotalWeights)
    TextView TextViewTotalWeights;




    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_wallet = new VM_Wallet(getContext());
            FragmentWalletBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_wallet, container, false
            );
            binding.setVMWallet(vm_wallet);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    public Wallet() {//_____________________________________________________________________ Start FragmentWallet
    }//_____________________________________________________________________________________________ End FragmentWallet


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setPublishSubjectFromObservable(
                Wallet.this,
                vm_wallet.getPublishSubject(),
                vm_wallet);
    }//_____________________________________________________________________________________________ End onStart


    @Override
    public void getActionFromObservable(Byte action) {//____________________________________________ GetMessageFromObservable

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetGiveScore)) {
            setAdapterGiveScoreListConfig();
            return;
        }

        if (action.equals(StaticValues.ML_GetReport)) {
            SetChart();
            return;
        }

        if (action.equals(StaticValues.ML_GetUserScorePriceReport)) {
            gifLoading.setVisibility(View.GONE);
            Integer totalPrice = (int) vm_wallet.getMd_userScorePriceReport().getTotalPrice();
            String string = getContext().getResources().getString(R.string.WalletMoney) + " " + totalPrice.toString();
            TextViewTotalPrice.setText(string);

            double weight = vm_wallet.getMd_userScorePriceReport().getTotalWeights();
            weight = weight / 1000;
            TextViewTotalWeights.setText(String.valueOf((int) weight));

            return;
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable



    //______________________________________________________________________________________________ init
    private void init() {

        chart.setVisibility(View.GONE);
        gifLoading.setVisibility(View.VISIBLE);
        vm_wallet.getGiveScoreList();

    }
    //______________________________________________________________________________________________ init



    //______________________________________________________________________________________________ setAdapterGiveScoreListConfig
    private void setAdapterGiveScoreListConfig() {

        AP_WalletScore ap_walletScore = new AP_WalletScore(vm_wallet.getScoreListConfigs());
        RecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewItem.setAdapter(ap_walletScore);
    }
    //______________________________________________________________________________________________ setAdapterGiveScoreListConfig





    private void SetChart() {//_____________________________________________________________________ Start SetChart


        months = new ArrayList<>();
        values = new ArrayList<>();
        for (int i = 0; i < vm_wallet.getMd_chartReports().size(); i++) {
            MD_ChartReport chartReport = vm_wallet.getMd_chartReports().get(i);
            months.add(chartReport.getMonthName());
            float value = (float) (chartReport.getScorePoint());
            values.add(new BarEntry(i, value));
        }

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true);
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
                return months.get((int) value % months.size());
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
        setData();

    }//_____________________________________________________________________________________________ End SetChart



    private void setData() {


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
            if (getContext()!= null) {
                colors.add(getContext().getResources().getColor(R.color.mlHomeMaxScore));
                colors.add(getContext().getResources().getColor(R.color.mlHomeAveScore));
            }
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

            chart.setVisibility(View.GONE);
            chart.setData(data);
            chart.setVisibility(View.VISIBLE);
        }
    }



}
