package tbc.techbytecare.kk.dream11client;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import tbc.techbytecare.kk.dream11client.Fragment.ContestFragment;
import tbc.techbytecare.kk.dream11client.Fragment.HomeFragment;
import tbc.techbytecare.kk.dream11client.Fragment.MoreFragment;
import tbc.techbytecare.kk.dream11client.Fragment.ProfileFragment;
import tbc.techbytecare.kk.dream11client.Helper.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId())   {

                    case R.id.action_home :
                        selectedFragment = HomeFragment.newInstance();
                        break;

                    case  R.id.action_contests :
                        selectedFragment = ContestFragment.newInstance();
                        break;

                    case R.id.action_me :
                        selectedFragment = ProfileFragment.newInstance();
                        break;

                    case R.id.action_more :
                        selectedFragment = MoreFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId())   {

                    case R.id.action_home :
                        selectedFragment = HomeFragment.newInstance();
                        break;

                    case  R.id.action_contests :
                        selectedFragment = ContestFragment.newInstance();
                        break;

                    case R.id.action_me :
                        selectedFragment = ProfileFragment.newInstance();
                        break;

                    case R.id.action_more :
                        selectedFragment = MoreFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame, selectedFragment);
                transaction.commit();
            }
        });
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, HomeFragment.newInstance());
        transaction.commit();
    }
}
