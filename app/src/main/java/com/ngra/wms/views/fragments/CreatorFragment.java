package com.ngra.wms.views.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCreatorBinding;
import com.ngra.wms.viewmodels.VM_CreatorFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatorFragment extends FragmentPrimary {

    @BindView(R.id.TextViewNgraSite)
    TextView TextViewNgraSite;

    @BindView(R.id.imgNgraLogo)
    ImageView imgNgraLogo;

    @BindView(R.id.tv_justified_paragraph)
    JustifiedTextView tv_justified_paragraph;

    @BindView(R.id.TextViewNgraTel)
    TextView TextViewNgraTel;


    //______________________________________________________________________________________________ CreatorFragment
    public CreatorFragment() {
        // Required empty public constructor
    }
    //______________________________________________________________________________________________ CreatorFragment


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            VM_CreatorFragment vm_creatorFragment = new VM_CreatorFragment(getActivity());
            FragmentCreatorBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_creator, container, false);
            binding.setCreator(vm_creatorFragment);
            setView(binding.getRoot());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setClicks();
        imgNgraLogo.setVisibility(View.INVISIBLE);
        TextViewNgraSite.setVisibility(View.INVISIBLE);
        tv_justified_paragraph.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(this::setAnimation, 500);

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ SetAnimation
    private void setAnimation() {

        Animation slide_in_left1 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        imgNgraLogo.setAnimation(slide_in_left1);
        imgNgraLogo.setVisibility(View.VISIBLE);

        Animation slide_in_left2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        TextViewNgraSite.setAnimation(slide_in_left2);
        TextViewNgraSite.setVisibility(View.VISIBLE);

        Animation slide_in_right = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        tv_justified_paragraph.setAnimation(slide_in_right);
        tv_justified_paragraph.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ SetAnimation


    //______________________________________________________________________________________________ setClicks
    private void setClicks() {

        TextViewNgraSite.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.ngra.ir"));
            startActivity(intent);
        });

        TextViewNgraTel.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + "02691009001"));
            if (getContext() != null)
                getContext().startActivity(callIntent);
        });

    }
    //______________________________________________________________________________________________ setClicks

}
