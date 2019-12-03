package com.example.wms.views.fragments.learn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnItemsBinding;
import com.example.wms.viewmodels.learn.FragmentLearnItemsViewModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentLearnItems extends Fragment {

    private Context context;
    private FragmentLearnItemsViewModel fragmentLearnItemsViewModel;

    @BindView(R.id.FLIPlasticExpandClick)
    LinearLayout FLIPlasticExpandClick;

    @BindView(R.id.FLIMetalExpandClick)
    LinearLayout FLIMetalExpandClick;

    @BindView(R.id.FLIGlassExpandClick)
    LinearLayout FLIGlassExpandClick;

    @BindView(R.id.FLIPaperExpandClick)
    LinearLayout FLIPaperExpandClick;

    @BindView(R.id.FLIElectronicsExpandClick)
    LinearLayout FLIElectronicsExpandClick;

    @BindView(R.id.FLINonRecyclableExpandClick)
    LinearLayout FLINonRecyclableExpandClick;

    @BindView(R.id.FLIPlasticExpand)
    ExpandableLayout FLIPlasticExpand;

    @BindView(R.id.FLIMetalExpand)
    ExpandableLayout FLIMetalExpand;

    @BindView(R.id.FLIGlassExpand)
    ExpandableLayout FLIGlassExpand;

    @BindView(R.id.FLIPaperExpand)
    ExpandableLayout FLIPaperExpand;

    @BindView(R.id.FLIElectronicsExpand)
    ExpandableLayout FLIElectronicsExpand;

    @BindView(R.id.FLINonRecyclableExpand)
    ExpandableLayout FLINonRecyclableExpand;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLearnItemsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn_items, container, false
        );
        fragmentLearnItemsViewModel = new FragmentLearnItemsViewModel(context);
        binding.setLearnitem(fragmentLearnItemsViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLearnItems(Context context) {//__________________________________________________ Start FragmentLearnItems
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLearnItems


    public FragmentLearnItems() {//_________________________________________________________________ Start FragmentLearnItems
    }//_____________________________________________________________________________________________ End FragmentLearnItems


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        CollapseExpandableLayouts();
        SetClickForExpandExpandableLayout();

    }//_____________________________________________________________________________________________ End onStart


    private void SetClickForExpandExpandableLayout() {//____________________________________________ Start SetClickForExpandExpandableLayout

        FLIPlasticExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIPlasticExpand.isExpanded())
                    FLIPlasticExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLIPlasticExpand.expand();

                }
            }
        });

        FLIMetalExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIMetalExpand.isExpanded())
                    FLIMetalExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLIMetalExpand.expand();
                }

            }
        });

        FLIGlassExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIGlassExpand.isExpanded())
                    FLIGlassExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLIGlassExpand.expand();
                }
            }
        });

        FLIPaperExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIPaperExpand.isExpanded())
                    FLIPaperExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLIPaperExpand.expand();
                }
            }
        });

        FLIElectronicsExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIElectronicsExpand.isExpanded())
                    FLIElectronicsExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLIElectronicsExpand.expand();
                }
            }
        });

        FLINonRecyclableExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLINonRecyclableExpand.isExpanded())
                    FLINonRecyclableExpand.collapse();
                else {
                    CollapseExpandableLayouts();
                    FLINonRecyclableExpand.expand();
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClickForExpandExpandableLayout


    private void CollapseExpandableLayouts() {//____________________________________________________ Start CollapseExpandableLayouts
        FLIPlasticExpand.collapse();
        FLIMetalExpand.collapse();
        FLIGlassExpand.collapse();
        FLIPaperExpand.collapse();
        FLIElectronicsExpand.collapse();
        FLINonRecyclableExpand.collapse();
    }//_____________________________________________________________________________________________ End CollapseExpandableLayouts
}
