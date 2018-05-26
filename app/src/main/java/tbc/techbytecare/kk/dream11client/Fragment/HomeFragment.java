package tbc.techbytecare.kk.dream11client.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import tbc.techbytecare.kk.dream11client.Common.Common;
import tbc.techbytecare.kk.dream11client.FixtureDetailActivity;
import tbc.techbytecare.kk.dream11client.HomeActivity;
import tbc.techbytecare.kk.dream11client.Model.Banner;
import tbc.techbytecare.kk.dream11client.NotifyActivity;
import tbc.techbytecare.kk.dream11client.R;

public class HomeFragment extends Fragment {

    ViewPager pager;
    //SectionsPagerAdapter sectionsPagerAdapter;

    FragmentPagerAdapter adapterViewPager;

    TabLayout tabLayout;

    HashMap<String,String> imageList;
    SliderLayout slider;

    FirebaseDatabase database;

    View myFragment;

    public HomeFragment() {
    }

    public static HomeFragment newInstance()    {
        HomeFragment homeFragment = new HomeFragment();

        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myFragment =  inflater.inflate(R.layout.fragment_home, container, false);

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
        //sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        adapterViewPager = new MyPagerAdapter(getFragmentManager());
        pager.setAdapter(adapterViewPager);
        pager.setCurrentItem(0);

        tabLayout = myFragment.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        setUpSlider();

        return myFragment;
    }

    private void setUpSlider() {

        slider = myFragment.findViewById(R.id.slider);
        imageList = new HashMap<>();

        final DatabaseReference banners = database.getReference("Banners");

        banners.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())  {
                    Banner banner = postSnapshot.getValue(Banner.class);

                    imageList.put(banner.getName()+"@@@"+banner.getId(),banner.getImage());
                }

                for (String key : imageList.keySet())  {

                    String[] keySplit = key.split("@@@");
                    String nameOfFixture = keySplit[0];
                    String idOfFixture = keySplit[1];

                    //create slider
                    final TextSliderView textSliderView = new TextSliderView(getActivity());

                    textSliderView.description(nameOfFixture)
                            .image(imageList.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Intent intent = new Intent(getActivity(),FixtureDetailActivity.class);
                                    intent.putExtras(textSliderView.getBundle());
                                    startActivity(intent);
                                }
                            });
                    //add extra bundle
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FixtureId",idOfFixture);

                    slider.addSlider(textSliderView);

                    banners.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
    }

    @Override
    public void onResume() {
        super.onResume();
        slider.startAutoCycle();
    }

    @Override
    public void onStop() {
        super.onStop();
        slider.stopAutoCycle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.notification, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())   {

            case R.id.menu_notify :
                startActivity(new Intent(getActivity(), NotifyActivity.class));
        }

        return true;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return FixtureFragment.newInstance();
                case 1:
                    return LiveFragment.newInstance();
                case 2:
                    return ResultFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "FIXTURES";

                case 1:
                    return "LIVE";

                case 2:
                    return "RESULTS";
            }
            return null;
        }
    }
}
