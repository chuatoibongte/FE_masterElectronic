package com.example.mater_electronic.ui.activity.review;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;

import java.util.List;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.ImageViewHolder> {

    private List<Uri> imageUris;

    public ImagePickerAdapter(List<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_review_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.ivSelected.setImageURI(imageUris.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelected;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSelected = itemView.findViewById(R.id.ivSelected);
        }
    }
}

