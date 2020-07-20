package com.ngra.wms.views.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemLearnBinding;
import com.ngra.wms.databinding.AdapterItemOrderBinding;
import com.ngra.wms.models.MD_ItemLearn;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_LearnItem extends RecyclerView.Adapter<AP_LearnItem.CustomHolder> {

    private List<MD_ItemLearn> itemLearns;
    private LayoutInflater layoutInflater;

    public AP_LearnItem(List<MD_ItemLearn> itemLearns) {
        this.itemLearns = itemLearns;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_learn,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(itemLearns.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemLearns.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemLearnBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ImageViewArrow)
        ImageView ImageViewArrow;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;


        public CustomHolder(AdapterItemLearnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ItemLearn item, final int position) {
            binding.setLearn(item);
            ImageViewArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            LinearLayoutExpandClick.setOnClickListener(v -> {
                if (ExpandableLayoutItem.isExpanded()) {
                    ExpandableLayoutItem.collapse();
                    ImageViewArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    ExpandableLayoutItem.expand();
                    ImageViewArrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            });
            binding.executePendingBindings();
        }
    }
}
