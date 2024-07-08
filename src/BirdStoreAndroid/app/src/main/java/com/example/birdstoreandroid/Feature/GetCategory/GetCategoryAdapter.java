package com.example.birdstoreandroid.Feature.GetCategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.Model.GetCategoryRequest;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryAdapter extends RecyclerView.Adapter<GetCategoryAdapter.CategoryViewHolder> {
    private List<GetCategoryRequest> categories;

    private List<GetCategoryRequest> categoriesFull;

    public GetCategoryAdapter(List<GetCategoryRequest> categories) {
        this.categories = categories;

        this.categoriesFull = new ArrayList<>(categories);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        GetCategoryRequest category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void filter(String text) {
        categories.clear();
        if (text.isEmpty()) {
            categories.addAll(categoriesFull);
        } else {
            text = TextUtils.normalize(text.toLowerCase());
            for (GetCategoryRequest item : categoriesFull) {
                if (TextUtils.normalize(item.getCategory_name().toLowerCase()).contains(text)) {
                    categories.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTxt;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTxt = itemView.findViewById(R.id.category_name);
        }

        public void bind(GetCategoryRequest category) {
            categoryNameTxt.setText(category.getCategory_name());
        }
    }
}