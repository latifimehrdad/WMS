/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentHomeBinding;
import com.example.wms.viewmodels.main.FragmentHomeViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.custom.circlemenu.CircleMenuLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends Fragment implements OnChartValueSelectedListener {

    private Context context;
    private FragmentHomeViewModel fragmentHomeViewModel;
    private CircleMenuLayout mCircleMenuLayout;
    private String[] mItemTexts = new String[]{"درخواست جمع آوری", "قرعه کشی", "درخواست پکیج","تفکیک پسماند"};

    @BindView(R.id.fhTextAve)
    TextView fhTextAve;

    @BindView(R.id.piechart_1)
    PieChart pieChart;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false
        );
        fragmentHomeViewModel = new FragmentHomeViewModel(context);
        binding.setHome(fragmentHomeViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentHome(Context context) {//________________________________________________________ Start FragmentHome
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentHome


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.setDragDecelerationFrictionCoef(0.5f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setHoleColor(context.getResources().getColor(R.color.mlWhite));
        pieChart.setCenterText(context.getResources().getText(R.string.Letsnotthrowawaythefuture));
        pieChart.setCenterTextColor(context.getResources().getColor(R.color.colorAccent));
        pieChart.setCenterTextSizePixels(20f);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.mlWhite));
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleAlpha(30);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setEntryLabelTextSize(12);

        pieChart.setUsePercentValues(false);
        pieChart.setRotationAngle(45);
        pieChart.setTouchEnabled(true);
        pieChart.setClickable(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateY(700, Easing.EaseInCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(30f, mItemTexts[0]));
        yValues.add(new PieEntry(30f, mItemTexts[1]));
        yValues.add(new PieEntry(30f, mItemTexts[2]));
        yValues.add(new PieEntry(30f, mItemTexts[3]));

        List<Integer> colors = new ArrayList<>();
        colors.add(context.getResources().getColor(R.color.mlHomeMaxScore));
        colors.add(context.getResources().getColor(R.color.mlHomeAveScore));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(0f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);


//        Button btn = (Button) getView().findViewById(R.id.btn);
//        RadialMenuView radial_menu_view = (RadialMenuView) getView().findViewById(R.id.radial_menu_view);
//        MenuItemView itemOne = new MenuItemView(context ,"Ask Questions",R.drawable.a, R.color.mlCollectBooth);
//        MenuItemView itemTwo = new MenuItemView(context,"Friends",R.drawable.b, R.color.mlEdit);
//        MenuItemView itemThree = new MenuItemView(context,"Gallery", R.drawable.c, R.color.mlHomeAveScore);
//        MenuItemView itemFour = new MenuItemView(context,"Settings", R.drawable.d, R.color.mlHomeMaxScore);
//        MenuItemView itemFive = new MenuItemView(context, "Profile", R.drawable.e, R.color.mlCollectRight1);
//        ArrayList<MenuItemView> items = new ArrayList<>();
//        items.add(itemOne);
//        items.add(itemTwo);
//        items.add(itemThree);
//        items.add(itemFour);
//        items.add(itemFive);
//        radial_menu_view
//                .setListener(this)
//                .setMenuItems(items)
//                .setCenterView(btn)
//                .setInnerCircle(true, R.color.mlWhite)
//                .setOffset(10)
//                .build();
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radial_menu_view.show();
//            }
//        });

//        CustomView ml = (CustomView) getView().findViewById(R.id.ml);
//
//        mCircleMenuLayout = (CircleMenuLayout) getView().findViewById(R.id.id_menulayout);
//        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
//        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
//
//            @Override
//            public void itemClick(View view, int pos) {
//                switch (pos) {
//                    case 0:
//                        MainActivity.FragmentMessage.onNext("Learn");
//                        break;
//                    case 1:
//                        MainActivity.FragmentMessage.onNext("CollectRequest");
//                        break;
//
//                    case 2:
//                        MainActivity.FragmentMessage.onNext("Lottery");
//                        break;
//
//                    case 3:
//                        //context.ShowFragmentPAckRequest();
//                        MainActivity.FragmentMessage.onNext("PckRequest");
//                        break;
//                }
//            }
//
//            @Override
//            public void itemCenterClick(View view) {
//
//            }
//        });

        //ButterKnife.bind(this,getView());


    }//_____________________________________________________________________________________________ End onStart

//    @Override
//    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//        Toast.makeText(context,"onChartGestureStart",Toast.LENGTH_SHORT).show();
//        Log.i("meri" , "" + me.getButtonState());
//        Log.i("meri" , "" + me.getFlags());
//        Log.i("meri" , "" + me.getMetaState());
//        Log.i("meri" , "" + me.getPointerCount());
//        Log.i("meri" , "" + me.getPressure());
//
//
//
//
//    }
//
//    @Override
//    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//    }
//
//    @Override
//    public void onChartLongPressed(MotionEvent me) {
//        Toast.makeText(context,"onChartLongPressed " ,Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onChartDoubleTapped(MotionEvent me) {
//        Toast.makeText(context,"onChartDoubleTapped",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onChartSingleTapped(MotionEvent me) {
//
//    }
//
//    @Override
//    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//
//    }
//
//    @Override
//    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//
//    }
//
//    @Override
//    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//
//    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        switch ((int) h.getX()) {
            case 0:
                MainActivity.FragmentMessage.onNext("CollectRequest");

                break;
            case 1:
                MainActivity.FragmentMessage.onNext("Lottery");
                break;

            case 2:
                MainActivity.FragmentMessage.onNext("PckRequest");
                break;

            case 3:
                MainActivity.FragmentMessage.onNext("Learn");
                break;
        }


    }

    @Override
    public void onNothingSelected() {

    }


}
