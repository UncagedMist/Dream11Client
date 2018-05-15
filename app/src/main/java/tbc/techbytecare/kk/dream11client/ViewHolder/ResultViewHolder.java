package tbc.techbytecare.kk.dream11client.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tbc.techbytecare.kk.dream11client.R;

public class ResultViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgFirstOpponent,imgSecondOpponent;
    public TextView txtSeriesName,txtTimer;

    public ResultViewHolder(View itemView) {
        super(itemView);

        imgFirstOpponent = itemView.findViewById(R.id.imgFirstOpponent);
        imgSecondOpponent = itemView.findViewById(R.id.imgSecondOpponent);

        txtSeriesName = itemView.findViewById(R.id.txtSeriesName);
        txtTimer = itemView.findViewById(R.id.txtTimer);
    }
}
