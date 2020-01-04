/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.wms.views.custom.textview.VerticalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.views.activitys.MainActivity.complateprofile;

public class FragmentHome extends Fragment {

    private Context context;
    private VM_FragmentHome vm_fragmentHome;
    boolean doubleBackToExitPressedOnce = false;
    private NavController navController;
    private View view;

    @BindView(R.id.footerprimery)
    RelativeLayout footerprimery;

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
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentHomeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false
        );
        vm_fragmentHome = new VM_FragmentHome(context);
        binding.setHome(vm_fragmentHome);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentHome() {//_______________________________________________________________________ Start FragmentHome

    }//_____________________________________________________________________________________________ End FragmentHome


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        SetLayout();
        SetClick();
        CheckProfile();
    }//_____________________________________________________________________________________________ End onStart




    private void SetClick() {//_____________________________________________________________________ Start SetClick


        footerup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequest);
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

    }//_____________________________________________________________________________________________ End SetClick


    private void SetLayout() {//____________________________________________________________________ Start SetLayout

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = footerprimery.getMeasuredWidth();
                int height = footerprimery.getMeasuredHeight();

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
    }//_____________________________________________________________________________________________ End SetLayout


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKeyExit(view));
    }//_____________________________________________________________________________________________ End BackClick


    public View.OnKeyListener SetKeyExit(View view) {//_____________________________________________ Start SetBackClickAndGoHome
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (!doubleBackToExitPressedOnce) {
                    Toast.makeText(context,"برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();
                    doubleBackToExitPressedOnce = true;
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    return true;
                } else
                    return false;
            }
        };
    }//_____________________________________________________________________________________________ End SetBackClickAndGoHome


    private void CheckProfile() {//_________________________________________________________________ Start CheckProfile

        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs == null) {

        } else {
            complateprofile = prefs.getBoolean("complateprofile", false);
            if (complateprofile == false)
                navController.navigate(R.id.action_fragmentHome_to_fragmentProfile);
        }

    }//_____________________________________________________________________________________________ End CheckProfile


}
