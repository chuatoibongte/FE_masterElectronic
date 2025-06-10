package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.account.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<Address> addressList;
    private AddressActionListener listener;

    public AddressAdapter(List<Address> addressList, AddressActionListener listener) {
        this.addressList = addressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);

        holder.tvAddressUserName.setText(address.getName());
        holder.tvAddress.setText(address.getAddress());
        holder.tvPhone.setText(address.getPhone());

        // Handle edit button click
        holder.editBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditAddress(position, address);
            }
        });

        // Handle delete button click
        holder.deleteBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteAddress(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public void removeItem(int position) {
        if (addressList != null && position >= 0 && position < addressList.size()) {
            addressList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, addressList.size());
        }
    }

    public void updateAddressList(List<Address> newAddressList) {
        this.addressList = newAddressList;
        notifyDataSetChanged();
    }

    public List<Address> getCurrentAddressList() {
        return addressList;
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddressUserName;
        TextView tvAddress;
        TextView tvPhone;
        ImageView editBtn;
        ImageView deleteBtn;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddressUserName = itemView.findViewById(R.id.tvAddressUserName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}