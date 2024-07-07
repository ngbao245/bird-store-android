package com.example.birdstoreandroid.Feature.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImg;
        private TextView titleTxt;
        private TextView itemPriceTxt;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.imageViewProduct);
            titleTxt = itemView.findViewById(R.id.textViewProductName);
            itemPriceTxt = itemView.findViewById(R.id.textViewProductPrice);
        }

        public void bind(CartItem cartItem) {
            Picasso.get().load(cartItem.getProduct().getImage()).into(itemImg);

            titleTxt.setText(cartItem.getProduct().getName());
            itemPriceTxt.setText(String.valueOf(cartItem.getProduct().getPrice()));
        }
    }
}
