package com.ngra.wms.views.adaptors.collectrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemDateBinding;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_TimeSheet extends RecyclerView.Adapter<AP_TimeSheet.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_TimeSheet> md_timeSheets;
    private ItemTimeClick itemTimeClick;

    private Integer Selected;


    public AP_TimeSheet(List<MD_TimeSheet> md_timeSheets, ItemTimeClick itemTimeClick, Integer Selected) {
        this.md_timeSheets = md_timeSheets;
        this.itemTimeClick = itemTimeClick;
        this.Selected = Selected;
    }

    public interface ItemTimeClick {//______________________________________________________________ ItemTimeClick
        void itemTimeClick(Integer position);
    }//_____________________________________________________________________________________________ ItemTimeClick



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater,R.layout.adapter_item_date, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_timeSheets.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_timeSheets.size();
    }




    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemDateBinding binding;

        Context context;

        @BindView(R.id.LinearLayoutDate)
        LinearLayout LinearLayoutDate;


        public CustomHolder(AdapterItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_TimeSheet item, Integer Position) {
            ApplicationUtility component = ApplicationWMS
                    .getApplicationWMS(context)
                    .getUtilityComponent()
                    .getApplicationUtility();
            ApplicationUtility.MD_GregorianToSun toSun = component.GregorianToSun(item.getDate());
            binding.setDateJ(toSun);
            binding.getRoot().setOnClickListener(v -> itemTimeClick.itemTimeClick(Position));

            if (Selected != Position)
                LinearLayoutDate.setBackground(context.getResources().getDrawable(R.drawable.dw_back_day_time_sheet_down));
            else
                LinearLayoutDate.setBackground(context.getResources().getDrawable(R.drawable.dw_back_day_time_sheet_down_selected));


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
