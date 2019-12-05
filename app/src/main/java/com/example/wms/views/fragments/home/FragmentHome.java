/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentHomeBinding;
import com.example.wms.viewmodels.main.FragmentHomeViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.custom.circlemenu.CircleMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends Fragment {

    private Context context;
    private FragmentHomeViewModel fragmentHomeViewModel;
    private CircleMenuLayout mCircleMenuLayout;
    private String[] mItemTexts = new String[] { "تفکیک پسماند", "درخواست جمع آوری", "قرعه کشی", "درخواست پکیج"};
    private int[] mItemImgs = new int[] {
            R.drawable.a,
            R.drawable.b, R.drawable.c,
            R.drawable.d};


    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home,container,false
        );
        fragmentHomeViewModel = new FragmentHomeViewModel(context);
        binding.setHome(fragmentHomeViewModel);
        View view = binding.getRoot();

        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentHome(Context context) {//________________________________________________________ Start FragmentHome
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentHome


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        int i = 0;

        mCircleMenuLayout = (CircleMenuLayout) getView().findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
        {

            @Override
            public void itemClick(View view, int pos)
            {
                switch (pos){
                    case 0:
                        MainActivity.FragmentMessage.onNext("Learn");
                        break;
                    case 1:
                        MainActivity.FragmentMessage.onNext("CollectRequest");
                        break;

                    case 2:
                        MainActivity.FragmentMessage.onNext("Lottery");
                        break;

                    case 3:
                        //context.ShowFragmentPAckRequest();
                        MainActivity.FragmentMessage.onNext("PckRequest");
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view)
            {

            }
        });

        //ButterKnife.bind(this,getView());


    }//_____________________________________________________________________________________________ End onStart



}
