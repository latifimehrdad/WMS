package com.example.wms.views.fragments.home;


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
import com.example.wms.R;
import com.example.wms.databinding.FragmentCreatorBinding;
import com.example.wms.viewmodels.main.VM_CreatorFragment;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatorFragment extends FragmentPrimary {

    @BindView(R.id.NgraSite)
    TextView NgraSite;

    @BindView(R.id.imgNgraLogo)
    ImageView imgNgraLogo;

    @BindView(R.id.tv_justified_paragraph)
    JustifiedTextView tv_justified_paragraph;

    @BindView(R.id.NgraTel)
    TextView NgraTel;

    public CreatorFragment() {//____________________________________________________________________ Start CreatorFragment
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End CreatorFragment


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() != null) {
            VM_CreatorFragment vm_creatorFragment = new VM_CreatorFragment(getActivity());
            FragmentCreatorBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_creator, container, false);
            binding.setCreator(vm_creatorFragment);
            setView(getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetClicks();
        imgNgraLogo.setVisibility(View.INVISIBLE);
        NgraSite.setVisibility(View.INVISIBLE);
        tv_justified_paragraph.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(this::SetAnimation, 500);

    }//_____________________________________________________________________________________________ End onStart


    private void SetAnimation() {//_________________________________________________________________ Start SetAnimation

        Animation slide_in_left1 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        imgNgraLogo.setAnimation(slide_in_left1);
        imgNgraLogo.setVisibility(View.VISIBLE);

        Animation slide_in_left2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        NgraSite.setAnimation(slide_in_left2);
        NgraSite.setVisibility(View.VISIBLE);

        Animation slide_in_right = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        tv_justified_paragraph.setAnimation(slide_in_right);
        tv_justified_paragraph.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End SetAnimation


    private void SetClicks() {//____________________________________________________________________ Start SetClicks
        NgraSite.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.ngra.ir"));
            startActivity(intent);
        });

        NgraTel.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + "02691009001"));
            if (getContext() != null)
                getContext().startActivity(callIntent);
        });

    }//_____________________________________________________________________________________________ End SetClicks

}
