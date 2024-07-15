package com.example.birdstoreandroid.Feature.PhoiGiong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.Model.GetAllPhoigiongResponse;
import com.example.birdstoreandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoiGiongAdapter extends RecyclerView.Adapter<PhoiGiongAdapter.ViewHolder>{
    private List<GetAllPhoigiongResponse.PhoiGiongData> phoiGiongList;

    public PhoiGiongAdapter(List<GetAllPhoigiongResponse.PhoiGiongData> phoiGiongList) {
        this.phoiGiongList = phoiGiongList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phoigiong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetAllPhoigiongResponse.PhoiGiongData item = phoiGiongList.get(position);
        holder.nameMale.setText(item.getBird_Shop_Male_Detail().getName());
        holder.nameFemale.setText(item.getBird_Shop_Female_Detail().getName());

        Picasso.get()
                .load(item.getBird_Shop_Male_Detail().getImage())
                .into(holder.imageMale);

        Picasso.get()
                .load(item.getBird_Shop_Female_Detail().getImage())
                .into(holder.imageFemale);
    }

    @Override
    public int getItemCount() {
        return phoiGiongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMale, imageFemale;
        TextView nameMale, nameFemale;

        public ViewHolder(View itemView) {
            super(itemView);
            nameMale = itemView.findViewById(R.id.nameMale);
            nameFemale = itemView.findViewById(R.id.nameFemale);
            imageMale = itemView.findViewById(R.id.imageMale);
            imageFemale = itemView.findViewById(R.id.imageFemale);
        }
    }
}
