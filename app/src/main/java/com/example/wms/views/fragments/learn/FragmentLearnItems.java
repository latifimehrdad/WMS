package com.example.wms.views.fragments.learn;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.example.wms.viewmodels.learn.FragmentLearnItemsViewModel;
import com.example.wms.views.activitys.MainActivity;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLearnItemsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn_items, container, false
        );
        fragmentLearnItemsViewModel = new FragmentLearnItemsViewModel(context);
        binding.setLearnitem(fragmentLearnItemsViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
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


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick




    private View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetKey
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                keyCode = 0;
                event = null;
                MainActivity.FragmentMessage.onNext("Main");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey

}
