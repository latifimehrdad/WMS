package com.ngra.wms.views.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemTicketReplyBinding;
import com.ngra.wms.models.MD_TicketReplyDto;
import com.ngra.wms.utility.StaticValues;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_TicketReply extends RecyclerView.Adapter<AP_TicketReply.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_TicketReplyDto> md_ticketReplyDtos;

    public AP_TicketReply(List<MD_TicketReplyDto> md_ticketReplyDtos) {
        this.md_ticketReplyDtos = md_ticketReplyDtos;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_ticket_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_ticketReplyDtos.get(position));
    }

    @Override
    public int getItemCount() {
        return md_ticketReplyDtos.size();
    }


    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemTicketReplyBinding binding;

        @BindView(R.id.LinearLayoutOperator)
        LinearLayout LinearLayoutOperator;

        @BindView(R.id.LinearLayoutSender)
        LinearLayout LinearLayoutSender;


        public CustomHolder(AdapterItemTicketReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_TicketReplyDto item) {
            binding.setReply(item);
            if (item.getType().equals(StaticValues.TicketReplyTypeOperator)) {
                LinearLayoutOperator.setVisibility(View.VISIBLE);
                LinearLayoutSender.setVisibility(View.GONE);
            } else {
                LinearLayoutOperator.setVisibility(View.GONE);
                LinearLayoutSender.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }
    }

}
