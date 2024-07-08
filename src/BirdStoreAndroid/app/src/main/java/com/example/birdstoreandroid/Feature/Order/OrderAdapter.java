package com.example.birdstoreandroid.Feature.Order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.Feature.Cart.CartAdapter;
import com.example.birdstoreandroid.Feature.Cart.CartItem;
import com.example.birdstoreandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private List<CartItem> orderItems;

    public OrderAdapter(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_no_image, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        CartItem orderItem = orderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems != null ? orderItems.size() : 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTxt;
        private TextView itemPriceTxt;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.textViewProductName);
            itemPriceTxt = itemView.findViewById(R.id.textViewProductPrice);
        }

        public void bind(CartItem orderItem) {
            titleTxt.setText(orderItem.getProduct().getName());
            itemPriceTxt.setText(String.valueOf(orderItem.getProduct().getPrice()));
        }
    }
}
