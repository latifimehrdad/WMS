package com.ngra.wms.views.adaptors.collectrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemDateBinding;
import com.ngra.wms.databinding.AdapterItemTimeBinding;
import com.ngra.wms.models.MD_Time;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_TimeSheetItem extends RecyclerView.Adapter<AP_TimeSheetItem.CustomHolder> {


    private Integer Selected;
    private LayoutInflater layoutInflater;
    private List<MD_Time> md_times;
    private ItemTimeItemClick itemTimeItemClick;


    public interface ItemTimeItemClick {//______________________________________________________________ ItemTimeClick
        void itemTimeItemClick(Integer position);
    }//_____________________________________________________________________________________________ ItemTimeClick


    public AP_TimeSheetItem(List<MD_Time> md_times, ItemTimeItemClick itemTimeItemClick) {
        this.md_times = md_times;
        this.itemTimeItemClick = itemTimeItemClick;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_times.get(position), position, getSelected());
    }

    @Override
    public int getItemCount() {
        return md_times.size();
    }



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemTimeBinding binding;
        Context context;

        @BindView(R.id.LinearLayoutBack)
        LinearLayout LinearLayoutBack;


        public CustomHolder(AdapterItemTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Time item, Integer Position, Integer selected) {

            binding.setTime(item);
            binding.setSelected(selected);

            binding.getRoot().setOnClickListener(v -> itemTimeItemClick.itemTimeItemClick(Position));

            if (selected == -2) {
                LinearLayoutBack.setBackground(context.getResources().getDrawable(R.drawable.dw_back_item_time_sheet_disable));
                LinearLayoutBack.setAlpha(0.4f);
            } else if (selected == Position) {
                LinearLayoutBack.setBackground(context.getResources().getDrawable(R.drawable.dw_back_item_time_selected));
/*                LinearLayoutBack.setAlpha(1f);*/
            } else {
                LinearLayoutBack.setBackground(context.getResources().getDrawable(R.drawable.dw_back_item_ticket));
/*                LinearLayoutBack.setAlpha(1f);*/
            }

            binding.executePendingBindings();
        }
    }


    public Integer getSelected() {
        return Selected;
    }

    public void setSelected(Integer selected) {
        Selected = selected;
    }
}
