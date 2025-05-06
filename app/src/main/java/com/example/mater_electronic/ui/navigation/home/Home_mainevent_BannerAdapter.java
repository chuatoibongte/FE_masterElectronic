package com.example.mater_electronic.ui.navigation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;

import java.util.List;

public class Home_mainevent_BannerAdapter extends RecyclerView.Adapter<Home_mainevent_BannerAdapter.BannerViewHolder> {

    private final List<Home_mainevent_BannerItem> bannerList;

    public Home_mainevent_BannerAdapter(List<Home_mainevent_BannerItem> bannerList) {
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_mainevent_item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Home_mainevent_BannerItem item = bannerList.get(position % bannerList.size()); // Hỗ trợ vòng lặp vô hạn
        holder.imageBanner.setImageResource(item.getImageResId()); // Sử dụng setImageResource thay vì Picasso
    }

    @Override
    public int getItemCount() {
        return bannerList.isEmpty() ? 0 : Integer.MAX_VALUE; // Vòng lặp vô hạn
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBanner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBanner = itemView.findViewById(R.id.home_mainevent_imageBanner);
        }
    }
}