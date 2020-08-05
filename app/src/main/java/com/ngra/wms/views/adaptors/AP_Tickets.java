package com.ngra.wms.views.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemTicketBinding;
import com.ngra.wms.models.MD_UsersTicketList;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_Tickets extends RecyclerView.Adapter<AP_Tickets.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_UsersTicketList> md_usersTicketLists;
    private ItemTicketClick itemTicketClick;

    public interface ItemTicketClick {//____________________________________________________________ ItemTicketClick
        void itemChoose(Integer position);
    }//_____________________________________________________________________________________________ ItemTicketClick


    public AP_Tickets(List<MD_UsersTicketList> md_usersTicketLists, ItemTicketClick itemTicketClick) {
        this.md_usersTicketLists = md_usersTicketLists;
        this.itemTicketClick = itemTicketClick;
    }


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_ticket,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_usersTicketLists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_usersTicketLists.size();
    }




    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemTicketBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;

        public CustomHolder(AdapterItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_UsersTicketList item, final int position) {
            binding.setTicket(item);
            binding.getRoot().setOnClickListener(v ->itemTicketClick.itemChoose(position));
            LinearLayoutExpandClick.setOnClickListener(v -> {
                if (ExpandableLayoutItem.isExpanded())
                    ExpandableLayoutItem.collapse();
                else
                    ExpandableLayoutItem.expand();
            });
            binding.executePendingBindings();
        }
    }

}
