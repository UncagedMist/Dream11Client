package tbc.techbytecare.kk.dream11client.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import tbc.techbytecare.kk.dream11client.Common.Common;
import tbc.techbytecare.kk.dream11client.FixtureDetailActivity;
import tbc.techbytecare.kk.dream11client.Interface.ItemClickListener;
import tbc.techbytecare.kk.dream11client.Model.Fixture;
import tbc.techbytecare.kk.dream11client.R;
import tbc.techbytecare.kk.dream11client.ViewHolder.FixtureViewHolder;

public class FixtureFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private View itemView;

    FirebaseDatabase database;
    DatabaseReference fixtures;

    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseRecyclerAdapter<Fixture, FixtureViewHolder> adapter;

    public FixtureFragment() {
    }

    public static FixtureFragment newInstance()    {
        FixtureFragment fixtureFragment = new FixtureFragment();

        return fixtureFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        fixtures = database.getReference("Fixtures");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        itemView = inflater.inflate(R.layout.fragment_fixture,container,false);

        recyclerView = itemView.findViewById(R.id.recyclerFixture);

        swipeRefreshLayout = itemView.findViewById(R.id.swipeLayout);

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

        loadAllFixtures();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return itemView;
    }

    private void loadAll() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadAllFixtures() {

        FirebaseRecyclerOptions<Fixture> allFixture = new FirebaseRecyclerOptions.Builder<Fixture>()
                .setQuery(fixtures,Fixture.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Fixture, FixtureViewHolder>(allFixture) {
            @Override
            protected void onBindViewHolder(@NonNull FixtureViewHolder holder, int position, @NonNull Fixture model) {

                holder.txtSeriesName.setText(model.getSeriesName());
                holder.txtTimer.setText(model.getTimeLeft());

                Picasso.with(getContext())
                        .load(model.getFirstOpponent())
                        .into(holder.imgFirstOpponent);

                Picasso.with(getContext())
                        .load(model.getSecondOpponent())
                        .into(holder.imgSecondOpponent);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        startActivity(new Intent(getActivity(), FixtureDetailActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fixtures_layout,null,false);

                return new FixtureViewHolder(itemView);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)    {
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
        {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
