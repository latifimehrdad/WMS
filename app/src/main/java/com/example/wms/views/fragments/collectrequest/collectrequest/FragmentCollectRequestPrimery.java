package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.FragmentCollectRequestPrimeryViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentCollectRequestPrimery extends Fragment {

    private Context context;
    private FragmentCollectRequestPrimeryViewModel fragmentCollectRequestPrimeryViewModel;

    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_primery, container, false
        );
        fragmentCollectRequestPrimeryViewModel = new FragmentCollectRequestPrimeryViewModel(context);
        binding.setCollectprimery(fragmentCollectRequestPrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCollectRequestPrimery() {//______________________________________________________ Start FragmentCollectRequestPrimery
    }//_____________________________________________________________________________________________ End FragmentCollectRequestPrimery


    public FragmentCollectRequestPrimery(Context context) {//_______________________________________ Start FragmentCollectRequestPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCollectRequestPrimery

    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetClicks();
    }//_____________________________________________________________________________________________ End onStart


    private void SetClicks() {//____________________________________________________________________ Start

        fcrpRecyclingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.FragmentMessage.onNext("RecyclingCar");
            }
        });

        fcrpBoothReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.FragmentMessage.onNext("BoothReceive");
            }
        });

    }//_____________________________________________________________________________________________ End



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick




}
