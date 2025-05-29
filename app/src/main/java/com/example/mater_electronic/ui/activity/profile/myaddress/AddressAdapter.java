package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.content.Intent;
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
    public AddressAdapter(List<Address> addressList) {
        this.addressList = addressList;
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
            Intent intent = new Intent(v.getContext(), EditMyAddress.class);

            // Pass address data and position
            intent.putExtra("address_position", position);
            intent.putExtra("address_name", address.getName());
            intent.putExtra("address_address", address.getAddress());
            intent.putExtra("address_phone", address.getPhone());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public void updateAddressList(List<Address> newAddressList) {
        this.addressList = newAddressList;
        notifyDataSetChanged();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddressUserName;
        TextView tvAddress;
        TextView tvPhone;
        ImageView editBtn;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddressUserName = itemView.findViewById(R.id.tvAddressUserName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            editBtn = itemView.findViewById(R.id.editBtn);
        }
    }
}