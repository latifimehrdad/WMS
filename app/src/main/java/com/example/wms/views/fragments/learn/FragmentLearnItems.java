package com.example.wms.views.fragments.learn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnItemsBinding;
import com.example.wms.viewmodels.learn.VM_FragmentLearnItems;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLearnItems extends Fragment {

    private Context context;
    private VM_FragmentLearnItems VMFragmentLearnItems;
    private View view;

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

    @BindView(R.id.fliPlacticImage)
    ImageView fliPlacticImage;

    @BindView(R.id.fliMetalImage)
    ImageView fliMetalImage;

    @BindView(R.id.fliGlassImage)
    ImageView fliGlassImage;

    @BindView(R.id.fliElectronicImage)
    ImageView fliElectronicImage;

    @BindView(R.id.fliRecyclableImage)
    ImageView fliRecyclableImage;

    @BindView(R.id.fliPaperImage)
    ImageView fliPaperImage;



    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentLearnItemsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn_items, container, false
        );
        VMFragmentLearnItems = new VM_FragmentLearnItems(context);
        binding.setLearnitem(VMFragmentLearnItems);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


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
                if (FLIPlasticExpand.isExpanded()) {
                    FLIPlasticExpand.collapse();
                    fliPlacticImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLIPlasticExpand.expand();
                    fliPlacticImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });

        FLIMetalExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIMetalExpand.isExpanded()) {
                    FLIMetalExpand.collapse();
                    fliMetalImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLIMetalExpand.expand();
                    fliMetalImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }

            }
        });

        FLIGlassExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIGlassExpand.isExpanded()) {
                    FLIGlassExpand.collapse();
                    fliGlassImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLIGlassExpand.expand();
                    fliGlassImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });

        FLIPaperExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIPaperExpand.isExpanded()) {
                    FLIPaperExpand.collapse();
                    fliPaperImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLIPaperExpand.expand();
                    fliPaperImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });

        FLIElectronicsExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLIElectronicsExpand.isExpanded()) {
                    FLIElectronicsExpand.collapse();
                    fliElectronicImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLIElectronicsExpand.expand();
                    fliElectronicImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });

        FLINonRecyclableExpandClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLINonRecyclableExpand.isExpanded()) {
                    FLINonRecyclableExpand.collapse();
                    fliRecyclableImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    CollapseExpandableLayouts();
                    FLINonRecyclableExpand.expand();
                    fliRecyclableImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
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

        fliPlacticImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        fliMetalImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        fliGlassImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        fliElectronicImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        fliRecyclableImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        fliPaperImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

    }//_____________________________________________________________________________________________ End CollapseExpandableLayouts


}
