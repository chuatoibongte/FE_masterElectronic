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

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<Address> addressList;
    private AddressActionListener listener;

    public AddressAdapter(List<Address> addressList, AddressActionListener listener) {
        this.addressList = addressList != null ? new ArrayList<>(addressList) : new ArrayList<>();
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
        if (addressList == null || position >= addressList.size()) {
            return;
        }

        Address address = addressList.get(position);
        if (address == null) {
            return;
        }

        // Set text with null checks
        holder.tvAddressUserName.setText(address.getName() != null ? address.getName() : "");
        holder.tvAddress.setText(address.getAddress() != null ? address.getAddress() : "");
        holder.tvPhone.setText(address.getPhone() != null ? address.getPhone() : "");

        // Handle edit button click - use holder.getAdapterPosition() for latest position
        holder.editBtn.setOnClickListener(v -> {
            if (listener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION &&
                        currentPosition < addressList.size()) {
                    listener.onEditAddress(currentPosition, addressList.get(currentPosition));
                }
            }
        });

        // Handle delete button click - use holder.getAdapterPosition() for latest position
        holder.deleteBtn.setOnClickListener(v -> {
            if (listener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION &&
                        currentPosition < addressList.size()) {
                    listener.onDeleteAddress(currentPosition);
                }
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
            // Update positions for remaining items
            if (position < addressList.size()) {
                notifyItemRangeChanged(position, addressList.size() - position);
            }
        }
    }

    public void updateAddressList(List<Address> newAddressList) {
        if (newAddressList != null) {
            this.addressList = new ArrayList<>(newAddressList);
        } else {
            this.addressList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void addAddress(Address address) {
        if (address != null) {
            if (addressList == null) {
                addressList = new ArrayList<>();
            }
            addressList.add(address);
            notifyItemInserted(addressList.size() - 1);
        }
    }

    public void updateAddress(int position, Address address) {
        if (addressList != null && position >= 0 && position < addressList.size() && address != null) {
            addressList.set(position, address);
            notifyItemChanged(position);
        }
    }

    public List<Address> getCurrentAddressList() {
        return addressList != null ? new ArrayList<>(addressList) : new ArrayList<>();
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