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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
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
        private TextView totalItemPrice;
        private ImageView plusCartBtn;
        private ImageView minusCartBtn;
        private TextView itemQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.itemImg);
            titleTxt = itemView.findViewById(R.id.titletxt);
            itemPriceTxt = itemView.findViewById(R.id.itemPricetxt);
            totalItemPrice = itemView.findViewById(R.id.totalItemPrice);
            plusCartBtn = itemView.findViewById(R.id.plusCartbtn);
            minusCartBtn = itemView.findViewById(R.id.minusCartbtn);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }

        public void bind(CartItem cartItem) {
            Picasso.get().load(cartItem.getProduct().getImage()).into(itemImg);

            titleTxt.setText(cartItem.getProduct().getName());
            itemPriceTxt.setText(String.valueOf(cartItem.getProduct().getPrice()));
            itemQuantity.setText(String.valueOf(cartItem.getQuantity()));

            int totalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
            totalItemPrice.setText(String.valueOf(totalPrice));

            plusCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = cartItem.getQuantity();
                    cartItem.setQuantity(currentQuantity + 1);
                    itemQuantity.setText(String.valueOf(cartItem.getQuantity()));

                    int updatedTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
                    totalItemPrice.setText(String.valueOf(updatedTotalPrice));

                    // TODO: Update cart item quantity on the server
                }
            });

            minusCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = cartItem.getQuantity();
                    if (currentQuantity > 1) {
                        cartItem.setQuantity(currentQuantity - 1);
                        itemQuantity.setText(String.valueOf(cartItem.getQuantity()));

                        int updatedTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
                        totalItemPrice.setText(String.valueOf(updatedTotalPrice));

                        // TODO: Update cart item quantity on the server
                    }
                }
            });
        }
    }
}
