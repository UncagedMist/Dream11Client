package tbc.techbytecare.kk.dream11client;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import tbc.techbytecare.kk.dream11client.Adapter.CustomAdapter;
import tbc.techbytecare.kk.dream11client.Common.Common;
import tbc.techbytecare.kk.dream11client.Fragment.SectionsPagerAdapter;
import tbc.techbytecare.kk.dream11client.Helper.BottomNavigationViewHelper;
import tbc.techbytecare.kk.dream11client.Model.Banner;

public class HomeActivity extends AppCompatActivity {

    ViewPager pager;
    SectionsPagerAdapter sectionsPagerAdapter;

    TabLayout tabLayout;

    HashMap<String,String> imageList;
    SliderLayout slider;

    FirebaseDatabase database;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();

        Toolbar toolbar =  findViewById(R.id.toolbar);
        final Spinner spinner = findViewById(R.id.spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this,
                R.layout.custom_spinner_color,getResources().getStringArray(R.array.game_category));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pager = findViewById(R.id.pager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(sectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorBack,android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,android.R.color.holo_blue_dark);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    loadAll();
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    loadAll();
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpSlider();
    }

    private void loadAll() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setUpSlider() {

        slider = findViewById(R.id.slider);
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
                    String nameOfFood = keySplit[0];
                    String idOfFood = keySplit[1];

                    //create slider
                    final TextSliderView textSliderView = new TextSliderView(getBaseContext());

                    textSliderView.description(nameOfFood)
                            .image(imageList.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Intent intent = new Intent(HomeActivity.this,FixtureDetailActivity.class);
                                    intent.putExtras(textSliderView.getBundle());
                                    startActivity(intent);
                                }
                            });
                    //add extra bundle
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FoodId",idOfFood);

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
    protected void onResume() {
        super.onResume();
        slider.startAutoCycle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        slider.stopAutoCycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_notify)   {
            startActivity(new Intent(HomeActivity.this,NotifyActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
