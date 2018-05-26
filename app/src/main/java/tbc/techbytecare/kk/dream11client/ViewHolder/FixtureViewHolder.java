package tbc.techbytecare.kk.dream11client.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tbc.techbytecare.kk.dream11client.Interface.ItemClickListener;
import tbc.techbytecare.kk.dream11client.R;

public class FixtureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgFirstOpponent,imgSecondOpponent;
    public TextView txtSeriesName,txtTimer;

    private ItemClickListener itemClickListener;

    public FixtureViewHolder(View itemView) {
        super(itemView);

        imgFirstOpponent = itemView.findViewById(R.id.imgFirstOpponent);
        imgSecondOpponent = itemView.findViewById(R.id.imgSecondOpponent);

        txtSeriesName = itemView.findViewById(R.id.txtSeriesName);
        txtTimer = itemView.findViewById(R.id.txtTimer);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
