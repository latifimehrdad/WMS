/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentHomeBinding;
import com.example.wms.viewmodels.main.VM_FragmentHome;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.custom.textview.VerticalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    private Context context;
    private VM_FragmentHome vm_fragmentHome;
    private NavController navController;
    private View view;

    @BindView(R.id.FooterPrimary)
    RelativeLayout FooterPrimary;

    @BindView(R.id.footer)
    RelativeLayout footer;

    @BindView(R.id.footerup)
    TextView footerup;

    @BindView(R.id.footerdown)
    TextView footerdown;

    @BindView(R.id.footerright)
    VerticalTextView footerright;

    @BindView(R.id.footerleft)
    VerticalTextView footerleft;

    @BindView(R.id.youScorelayout)
    LinearLayout youScorelayout;

    @BindView(R.id.scoreLayout)
    LinearLayout scoreLayout;

    @BindView(R.id.scoreLayoutChart)
    LinearLayout scoreLayoutChart;


    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            this.context = getActivity();
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container, false
            );
            vm_fragmentHome = new VM_FragmentHome(context);
            binding.setHome(vm_fragmentHome);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetLayout();
            SetClick();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView


    public FragmentHome() {//_______________________________________________________________________ FragmentHome

    }//_____________________________________________________________________________________________ FragmentHome


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        CheckProfile();
    }//_____________________________________________________________________________________________ onStart


    private void CheckProfile() {//___________________________________________________________________ CheckToken
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!vm_fragmentHome.CheckProfile()) {
                    navController.navigate(R.id.action_fragmentHome_to_fragmentProfile);
                }
            }
        }, 10);

    }//_____________________________________________________________________________________________ CheckToken


    private void SetClick() {//_____________________________________________________________________ SetClick


        footerup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vm_fragmentHome.IsPackageState()) {
//                    if (vm_fragmentHome.GetPackageState() == 3)
//                        ShowMessage(
//                                context.getResources().getString(R.string.GetPackage),
//                                getResources().getColor(R.color.mlWhite),
//                                getResources().getDrawable(R.drawable.ic_error));
//                    else
                        navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestPrimary);
                } else {
                    if (vm_fragmentHome.IsAddressCompleted())
                        navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestPrimary);
                    else
                        navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestAddress);


                }
            }
        });

        footerdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentCollectRequest);
            }
        });

        footerleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        footerright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLearn);
            }
        });


        youScorelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        scoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        scoreLayoutChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager());

    }//_____________________________________________________________________________________________ ShowMessage


    private void SetLayout() {//____________________________________________________________________ SetLayout

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = FooterPrimary.getMeasuredWidth();
                int height = FooterPrimary.getMeasuredHeight();

                if (width < height)
                    height = width;
                else
                    width = height;

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                footer.setLayoutParams(params);

                width = width / 4;
                ViewGroup.LayoutParams paramsUp = (ViewGroup.LayoutParams) footerup.getLayoutParams();
                paramsUp.height = width;
                paramsUp.width = width * 2;
                footerup.setLayoutParams(paramsUp);

                ViewGroup.LayoutParams paramsDown = (ViewGroup.LayoutParams) footerdown.getLayoutParams();
                paramsDown.height = width;
                paramsDown.width = width * 2;
                footerdown.setLayoutParams(paramsDown);

                ViewGroup.LayoutParams paramsLeft = (ViewGroup.LayoutParams) footerleft.getLayoutParams();
                paramsLeft.height = width * 2;
                paramsLeft.width = width;
                footerleft.setLayoutParams(paramsLeft);


                ViewGroup.LayoutParams paramsRight = (ViewGroup.LayoutParams) footerright.getLayoutParams();
                paramsRight.height = width * 2;
                paramsRight.width = width;
                footerright.setLayoutParams(paramsRight);


            }
        }, 10);
    }//_____________________________________________________________________________________________ SetLayout


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        ;
    }//_____________________________________________________________________________________________ onDestroy


    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
    }//_____________________________________________________________________________________________ onStop


}
