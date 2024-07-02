package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetProductAdapter extends BaseAdapter {

    private Context context;
    private List<GetProductRequest> products;

    public GetProductAdapter(Context context, List<GetProductRequest> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_product_item, parent, false);
        }

        GetProductRequest product = products.get(position);

        ImageView imageView = convertView.findViewById(R.id.ivProduct);
        TextView textViewName = convertView.findViewById(R.id.tvProductName);
        TextView textViewDescription = convertView.findViewById(R.id.tvProductDescription);
        TextView textViewPrice = convertView.findViewById(R.id.tvProductPrice);

        Picasso.get().load(product.getImage()).into(imageView);
        textViewName.setText(product.getName());
        textViewDescription.setText(product.getDescription());
        textViewPrice.setText(String.format("Price: %d", product.getPrice()));

        return convertView;
    }
}
