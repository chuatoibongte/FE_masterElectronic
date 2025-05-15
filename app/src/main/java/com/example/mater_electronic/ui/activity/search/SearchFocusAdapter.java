package com.example.mater_electronic.ui.activity.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.ui.activity.searchresult.SearchResultActivity;

import java.util.List;

public class SearchFocusAdapter extends RecyclerView.Adapter<SearchFocusAdapter.SearchFocusViewHolder> {
    private final List<String> mSearchHint;

    public SearchFocusAdapter(List<String> mSearchHint) {
        this.mSearchHint = mSearchHint;
    }

    @NonNull
    @Override
    public SearchFocusAdapter.SearchFocusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_focus_item, parent, false);
        return new SearchFocusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFocusAdapter.SearchFocusViewHolder holder, int position) {
        String searchHint = mSearchHint.get(position);
        holder.searchHint.setText(searchHint);
        holder.searchHint.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchResultActivity.class);
            v.getContext().startActivity(intent);
        });
        holder.imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchHint.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mSearchHint.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mSearchHint != null) return mSearchHint.size();
        return 0;
    }

    public static class SearchFocusViewHolder extends RecyclerView.ViewHolder {
        TextView searchHint;
        ImageView imgCross;
        public SearchFocusViewHolder(@NonNull View itemView) {
            super(itemView);
            searchHint = itemView.findViewById(R.id.tvSearchHint);
            imgCross = itemView.findViewById(R.id.imgCross);
        }
    }
}
