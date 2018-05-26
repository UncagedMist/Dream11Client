package tbc.techbytecare.kk.dream11client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import tbc.techbytecare.kk.dream11client.Common.Common;
import tbc.techbytecare.kk.dream11client.R;

public class ContestFragment extends Fragment {

    View myFragment;

    ViewPager pager;
    SectionsPagerAdapter sectionsPagerAdapter;

    TabLayout tabLayout;

    SwipeRefreshLayout swipeRefreshLayout;

    public ContestFragment() {
    }

    public static ContestFragment newInstance()    {
        ContestFragment contestFragment = new ContestFragment();

        return contestFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_contest, container, false);

        Toolbar toolbar = myFragment.findViewById(R.id.toolbar);
        final Spinner spinner = myFragment.findViewById(R.id.spinner);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_spinner_color,getResources().getStringArray(R.array.game_category));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pager = myFragment.findViewById(R.id.pager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        pager.setAdapter(sectionsPagerAdapter);

        tabLayout = myFragment.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        swipeRefreshLayout = myFragment.findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorBack,android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,android.R.color.holo_blue_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getActivity())) {
                    loadAll();
                }
                else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getActivity())) {
                    loadAll();
                }
                else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myFragment;
    }

    private void loadAll() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
