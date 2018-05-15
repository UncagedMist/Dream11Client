package tbc.techbytecare.kk.dream11client.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tbc.techbytecare.kk.dream11client.R;

public class CustomAdapter extends ArrayAdapter<String> {

    Context context;
    String[] catName;
    int[] catImages;

    public CustomAdapter(Context context,String[] catName, int[] catImages) {

        super(context, R.layout.spinner_row);
        this.context = context;
        this.catName = catName;
        this.catImages = catImages;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row,null);

        TextView txtName = row.findViewById(R.id.txtName);
        CircleImageView civPic = row.findViewById(R.id.civPic);

        txtName.setText(catName[position]);
        civPic.setImageResource(catImages[position]);

        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row,null);

        TextView txtName = row.findViewById(R.id.txtName);
        CircleImageView civPic = row.findViewById(R.id.civPic);

        txtName.setText(catName[position]);
        civPic.setImageResource(catImages[position]);

        return row;
    }
}
