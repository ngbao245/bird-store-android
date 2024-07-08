package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetProductAdapter extends RecyclerView.Adapter<GetProductAdapter.ViewHolder> {

    private Context context;
    private List<GetProductRequest> products;
    private OnItemClickListener onItemClickListener;

    private List<GetProductRequest> productsFull;

    public GetProductAdapter(Context context, List<GetProductRequest> products, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.products = products;
        this.onItemClickListener = onItemClickListener;

        this.productsFull = new ArrayList<>(products);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetProductRequest product = products.get(position);
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.format("Price: %d", product.getPrice()));

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void filter(String text) {
        products.clear();
        if (text.isEmpty()) {
            products.addAll(productsFull);
        } else {
            text = TextUtils.normalize(text.toLowerCase());
            for (GetProductRequest item : productsFull) {
                if (TextUtils.normalize(item.getName().toLowerCase()).contains(text)) {
                    products.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            textViewName = itemView.findViewById(R.id.product_name);
            textViewPrice = itemView.findViewById(R.id.product_price);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GetProductRequest product);
    }
}
