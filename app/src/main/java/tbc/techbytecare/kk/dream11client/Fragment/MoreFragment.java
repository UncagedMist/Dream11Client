package tbc.techbytecare.kk.dream11client.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbc.techbytecare.kk.dream11client.R;

public class MoreFragment extends Fragment {

    View myFragment;

    public MoreFragment() {
    }

    public static MoreFragment newInstance()    {
        MoreFragment moreFragment = new MoreFragment();

        return moreFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_more, container, false);

        Toolbar toolbar = myFragment.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("More");

        return myFragment;
    }

}
