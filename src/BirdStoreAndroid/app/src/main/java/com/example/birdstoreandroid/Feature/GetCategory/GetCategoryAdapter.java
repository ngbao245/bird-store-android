package com.example.birdstoreandroid.Feature.GetCategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.Model.GetCategoryRequest;
import com.example.birdstoreandroid.R;

import java.util.List;

    public class GetCategoryAdapter extends RecyclerView.Adapter<GetCategoryAdapter.CategoryViewHolder> {
        private List<GetCategoryRequest> categories;

        public GetCategoryAdapter(List<GetCategoryRequest> categories) {
            this.categories = categories;
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

